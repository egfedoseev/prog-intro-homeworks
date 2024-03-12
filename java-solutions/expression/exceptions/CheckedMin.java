package expression.exceptions;

import expression.AbstractExpression;

public class CheckedMin extends CheckedBinaryOperation {
    public CheckedMin(AbstractExpression first, AbstractExpression second) {
        super(first, second);
    }

    @Override
    protected String getSign() {
        return " min ";
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    protected boolean isCommutative() {
        return true;
    }

    @Override
    protected int calculateOperation(int x, int y) {
        return Math.min(x, y);
    }

    @Override
    protected void checkEvaluation(int x, int y) {
    }
}
