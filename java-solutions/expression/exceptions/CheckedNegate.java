package expression.exceptions;

import expression.AbstractExpression;

public class CheckedNegate extends ChackedUnaryOperation {
    public CheckedNegate(AbstractExpression expression) {
        super(expression);
    }

    @Override
    protected String getSign() {
        return "-";
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    protected int calculateOperation(int x) {
        return -x;
    }

    @Override
    protected void checkEvaluation(int x) throws EvaluationException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException(getSign(), x);
        }
    }
}
