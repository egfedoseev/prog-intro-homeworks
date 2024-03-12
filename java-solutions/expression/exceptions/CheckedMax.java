package expression.exceptions;

import expression.AbstractExpression;

public class CheckedMax extends CheckedBinaryOperation {
    public CheckedMax(AbstractExpression first, AbstractExpression second) {
        super(first, second);
    }

    @Override
    protected String getSign() {
        return " max ";
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
        return Math.max(x, y);
    }

    @Override
    protected void checkEvaluation(int x, int y) {
    }
}
