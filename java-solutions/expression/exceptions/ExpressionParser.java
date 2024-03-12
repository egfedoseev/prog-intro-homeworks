package expression.exceptions;

import expression.AbstractExpression;
import expression.Const;
import expression.TripleExpression;
import expression.Variable;
import expression.parser.CharSource;
import expression.parser.StringSource;
import expression.parser.TripleParser;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ExpressionParser implements TripleParser {
    private static final Map<String, Integer> PRIORITY = Map.of(
            "min", 1,
            "max", 1,
            "+", 4,
            "-", 4,
            "*", 5,
            "/", 5
    );
    private static final List<String> SIGNS = List.of(
            "min",
            "max",
            "+",
            "-",
            "*",
            "/"
    );
    private static final Map<Character, Character> PARENS = Map.of(
            '(', ')',
            '[', ']',
            '{', '}'
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

    protected boolean test(final String expected) {
        source.setCheckPoint();
        char prevCh = ch;
        boolean ret = true;
        for (int i = 0; i < expected.length(); ++i) {
            char c = take();
            if (c != expected.charAt(i)) {
                ret = false;
            }
        }
        source.returnToCheckPoint();
        ch = prevCh;
        return ret;
    }

    protected boolean take(final char expected) {
        if (test(expected)) {
            take();
            return true;
        }
        return false;
    }

    protected boolean take(String expected) {
        if (test(expected)) {
            for (int i = 0; i < expected.length(); ++i) {
                take();
            }
            return true;
        }
        return false;
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

        AbstractExpression parsedExpression = parseExpression(0);
        expect(END);

        return parsedExpression;
    }

    private AbstractExpression parseExpression(int minPriority) {
        skipWhitespace();
        AbstractExpression expression = null;

        expression = parseUnary();

        boolean skipped = skipWhitespace();

        while (true) {
            String sign = null;
            for (String s : SIGNS) {
                if ((Objects.equals(s, "min") || Objects.equals(s, "max")) && expression instanceof Const && !skipped) {
                    continue;
                }
                if (test(s)) {
                    sign = s;
                    break;
                }
            }

            if (sign == null || PRIORITY.get(sign) < minPriority) {
                break;
            }
            expression = parseBinaryOperation(expression);
            skipWhitespace();
        }

        return expression;
    }

    private boolean isParen() {
        return test('(') || test('[') || test('{');
    }

    private AbstractExpression parseUnary() {
        AbstractExpression expression = null;
        if (isParen()) {
            char open = take();
            expression = parseExpression(0);
            expect(PARENS.get(open));
        } else if (take('-')) {
            if (between('0', '9')) {
                expression = parseConst(-1);
            } else {
                expression = new CheckedNegate(parseExpression(MAX_PRIORITY));
            }
        } else if (between('0', '9')) {
            expression = parseConst(1);
        } else if (take('x')) {
            expression = parseVariable("x");
        } else if (take('y')) {
            expression = parseVariable("y");
        } else if (take('z')) {
            expression = parseVariable("z");
        }

        if (expression == null) {
            throw error("Expected unary operator, variable or const, found " + ch);
        }

        return expression;
    }

    private CheckedBinaryOperation parseBinaryOperation(AbstractExpression first) {
        if (take('*')) {
            return new CheckedMultiply(first, parseExpression(6));
        } else if (take('/')) {
            return new CheckedDivide(first, parseExpression(6));
        } else if (take('+')) {
            return new CheckedAdd(first, parseExpression(5));
        } else if (take('-')) {
            return new CheckedSubtract(first, parseExpression(5));
        } else if (take("min")) {
            return new CheckedMin(first, parseExpression(2));
        } else if (take("max")) {
            return new CheckedMax(first, parseExpression(2));
        }
        throw error("Expected binary operator, found " + ch);
    }

    private Variable parseVariable(String name) {
        take(name);
        return new Variable(name);
    }

    private Const parseConst(int isMinus) {
        StringBuilder sb = new StringBuilder();
        if (isMinus == -1) {
            sb.append('-');
        }
        takeInteger(sb);
        int x = Integer.parseInt(sb.toString());
        if (!Integer.toString(x).contentEquals(sb)) {
            System.err.println(x + " " + sb);
            throw error("Const " + sb + " is not in [-2^31; 2^31-1]");
        }
        return new Const(x);
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

    private boolean skipWhitespace() {
        boolean ret = false;
        while (Character.isWhitespace(ch)) {
            take();
            ret = true;
        }
        return ret;
    }
}
