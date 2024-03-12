package expression;

import java.math.BigDecimal;

public class Subtract extends BinaryOperation {
    public Subtract(AbstractExpression first, AbstractExpression second) {
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
    protected BigDecimal calculateOperation(BigDecimal x, BigDecimal y) {
        return x.subtract(y);
    }
}
