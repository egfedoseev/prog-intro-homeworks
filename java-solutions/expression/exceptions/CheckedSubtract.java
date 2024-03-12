package expression.exceptions;

import expression.AbstractExpression;

public class CheckedSubtract extends CheckedBinaryOperation {
    public CheckedSubtract(AbstractExpression first, AbstractExpression second) {
        super(first, second);
    }

    @Override
    public int getPriority() {
        return 4;
    }

    @Override
    protected boolean isCommutative() {
        return false;
    }

    @Override
    protected String getSign() {
        return " - ";
    }

    @Override
    protected int calculateOperation(int x, int y) {
        return x - y;
    }

    @Override
    protected void checkEvaluation(int x, int y) throws OverflowException {
        if ((x >= 0 && y == Integer.MIN_VALUE) ||
                (x > 0 && y < 0 && Integer.MAX_VALUE - x < -y) ||
                (x < 0 && y > 0 && Integer.MIN_VALUE - x > -y)) {
            throw new OverflowException(x, y, getSign());
        }
    }
}
