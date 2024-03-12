package expression.exceptions;

import expression.AbstractExpression;

public class CheckedMultiply extends CheckedBinaryOperation {
    public CheckedMultiply(AbstractExpression first, AbstractExpression second) {
        super(first, second);
    }

    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    protected boolean isCommutative() {
        return true;
    }

    @Override
    protected String getSign() {
        return " * ";
    }

    @Override
    protected int calculateOperation(int x, int y) {
        return x * y;
    }

    @Override
    protected void checkEvaluation(int x, int y) throws OverflowException {
        if ((y != 0 && (x * y) / y != x) ||
                (x == Integer.MAX_VALUE && Math.abs(y) > 1) ||
                (y == Integer.MAX_VALUE && Math.abs(x) > 1) ||
                (x == Integer.MIN_VALUE && (y <= -1 || y > 1)) ||
                (y == Integer.MIN_VALUE && (x <= -1 || x > 1))) {
            throw new OverflowException(x, y, getSign());
        }
    }
}
