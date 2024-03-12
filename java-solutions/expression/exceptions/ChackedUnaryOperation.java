package expression.exceptions;

import expression.AbstractExpression;
import expression.UnaryOperation;

import java.math.BigDecimal;

public abstract class ChackedUnaryOperation extends UnaryOperation {
    public ChackedUnaryOperation(AbstractExpression expression) {
        super(expression);
    }

    protected abstract void checkEvaluation(int x) throws EvaluationException;

    @Override
    public int evaluate(int x) throws EvaluationException {
        int arg = expression.evaluate(x);
        checkEvaluation(arg);
        return calculateOperation(arg);
    }

    @Override
    public int evaluate(int x, int y, int z) throws EvaluationException {
        int arg = expression.evaluate(x, y, z);
        checkEvaluation(arg);
        return calculateOperation(arg);
    }

    @Override
    protected BigDecimal calculateOperation(BigDecimal x) {
        throw new UnsupportedOperationException("BigDecimal evaluation is unsupported");
    }
}
