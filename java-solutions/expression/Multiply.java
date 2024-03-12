package expression;

import java.math.BigDecimal;

public class Multiply extends BinaryOperation {
    public Multiply(AbstractExpression first, AbstractExpression second) {
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
    protected BigDecimal calculateOperation(BigDecimal x, BigDecimal y) {
        return x.multiply(y);
    }
}
