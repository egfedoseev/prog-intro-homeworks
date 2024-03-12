package expression.exceptions;

import expression.AbstractExpression;

public class CheckedDivide extends CheckedBinaryOperation {
    public CheckedDivide(AbstractExpression first, AbstractExpression second) {
        super(first, second);
    }

    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    protected boolean isCommutative() {
        return false;
    }

    @Override
    protected String getSign() {
        return " / ";
    }

    @Override
    protected int calculateOperation(int x, int y) {
        return x / y;
    }

    @Override
    protected void checkEvaluation(int x, int y) throws EvaluationException {
        if (y == 0) {
            throw new DivisionByZeroException(x, y, getSign());
        }
        if (x == Integer.MIN_VALUE && y == -1) {
            throw new OverflowException(x, y, getSign());
        }
    }
}
