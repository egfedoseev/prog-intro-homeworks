package expression.parser;

import expression.*;
import expression.exceptions.CheckedSubtract;

import java.util.Map;

public class ExpressionParser implements TripleParser {
    private static final Map<String, Integer> PRIORITY = Map.of(
            "|", 1,
            "^", 2,
            "&", 3,
            "+", 4,
            "-", 4,
            "*", 5,
            "/", 5
    );

    private static final int MAX_PRIORITY = 6;

    private static final char END = '\0';
    private CharSource source;
    private char ch = 0xffff;

    protected char take() {
        final char result = ch;
        ch = source.hasNext() ? source.next() : END;
        return result;
    }

    protected boolean test(final char expected) {
        return ch == expected;
    }

    protected boolean take(final char expected) {
        if (test(expected)) {
            take();
            return true;
        }
        return false;
    }

    protected boolean take(String expected) {
        source.setCheckPoint();
        char prevCh = ch;
        for (int i = 0; i < expected.length(); ++i) {
            if (!take(expected.charAt(i))) {
                source.returnToCheckPoint();
                ch = prevCh;
                return false;
            }
        }
        return true;
    }

    protected void expect(final char expected) {
        if (!take(expected)) {
            throw error("Expected '" + expected + "', found '" + ch + "'");
        }
    }

    protected void expect(final String value) {
        for (final char c : value.toCharArray()) {
            expect(c);
        }
    }

    protected boolean eof() {
        return take(END);
    }

    protected IllegalArgumentException error(final String message) {
        return source.error(message);
    }

    protected boolean between(final char from, final char to) {
        return from <= ch && ch <= to;
    }

    @Override
    public TripleExpression parse(String expression) {
        source = new StringSource(expression);
        take();

        return parseTripleExpression(0);
    }

    private AbstractExpression parseTripleExpression(int minPriority) {
        skipWhitespace();
        AbstractExpression expression = null;

        expression = parseUnary();

        skipWhitespace();

        while ((test('*') || test('/') || test('+') || test('-') ||
                test('&') || test('^') || test('|')) &&
                PRIORITY.get(String.valueOf(ch)) >= minPriority) {
            expression = parseBinaryOperation(expression);
            skipWhitespace();
        }

        return expression;
    }

    private AbstractExpression parseUnary() {
        AbstractExpression expression = null;
        if (take('(')) {
            expression = parseTripleExpression(0);
            expect(')');
        } else if (take('-')) {
            if (between('0', '9')) {
                expression = parseConst(-1);
            } else {
                expression = new Negate(parseTripleExpression(MAX_PRIORITY));
            }
        } else if (take("l1")) {
            expression = new UnaryL1(parseTripleExpression(MAX_PRIORITY));
        } else if (take("low")) {
            expression = new Low(parseTripleExpression(MAX_PRIORITY));
        } else if (take("t1")) {
            expression = new UnaryT1(parseTripleExpression(MAX_PRIORITY));
        } else if (take("high")) {
            expression = new High(parseTripleExpression(MAX_PRIORITY));
        } else if (test('x') || test('y') || test('z')) {
            expression = parseVariable();
        } else if (between('0', '9')) {
            expression = parseConst(1);
        }

        return expression;
    }

    private BinaryOperation parseBinaryOperation(AbstractExpression first) {
        if (take('*')) {
            return new Multiply(first, parseTripleExpression(6));
        } else if (take('/')) {
            return new Divide(first, parseTripleExpression(6));
        } else if (take('+')) {
            return new Add(first, parseTripleExpression(5));
        } else if (take('-')) {
            return new Subtract(first, parseTripleExpression(5));
        } else if (take('&')) {
            return new And(first, parseTripleExpression(4));
        } else if (take('^')) {
            return new Xor(first, parseTripleExpression(3));
        } else if (take('|')) {
            return new Or(first, parseTripleExpression(2));
        }

        return null;
    }

    private Variable parseVariable() {
        return new Variable(String.valueOf(take()));
    }

    private Const parseConst(int isMinus) {
        StringBuilder sb = new StringBuilder();
        takeInteger(sb);
        return new Const(Integer.parseUnsignedInt(sb.toString()) * isMinus);
    }

    private void takeInteger(final StringBuilder sb) {
        if (take('-')) {
            sb.append('-');
        }
        if (take('0')) {
            sb.append('0');
        } else if (between('1', '9')) {
            takeDigits(sb);
        } else {
            throw error("Invalid number");
        }
    }

    private void takeDigits(final StringBuilder sb) {
        while (between('0', '9')) {
            sb.append(take());
        }
    }

    private void skipWhitespace() {
        while (Character.isWhitespace(ch)) {
            take();
        }
    }
}
