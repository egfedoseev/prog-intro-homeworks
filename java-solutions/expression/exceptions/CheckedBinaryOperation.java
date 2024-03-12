package expression.exceptions;

import expression.AbstractExpression;
import expression.BinaryOperation;

import java.math.BigDecimal;

public abstract class CheckedBinaryOperation extends BinaryOperation {

    public CheckedBinaryOperation(AbstractExpression first, AbstractExpression second) {
        super(first, second);
    }

    protected abstract void checkEvaluation(int x, int y) throws EvaluationException;

    @Override
    public int evaluate(int x) throws EvaluationException {
        int left = first.evaluate(x);
        int right = second.evaluate(x);
        checkEvaluation(left, right);
        return calculateOperation(left, right);
    }

    @Override
    public int evaluate(int x, int y, int z) throws EvaluationException {
        int left = first.evaluate(x, y, z);
        int right = second.evaluate(x, y, z);
        checkEvaluation(left, right);
        return calculateOperation(left, right);
    }

    @Override
    protected BigDecimal calculateOperation(BigDecimal x, BigDecimal y) {
        throw new UnsupportedOperationException("Checked BigDecimal evaluation is unsupported");
    }
}
