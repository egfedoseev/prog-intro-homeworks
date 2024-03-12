package expression;

import java.math.BigDecimal;

public class Divide extends BinaryOperation {
    public Divide(AbstractExpression first, AbstractExpression second) {
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
    protected BigDecimal calculateOperation(BigDecimal x, BigDecimal y) {
        return x.divide(y);
    }
}
