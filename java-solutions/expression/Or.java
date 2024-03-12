package expression;

import java.math.BigDecimal;

public class Or extends BinaryOperation {
    public Or(AbstractExpression first, AbstractExpression second) {
        super(first, second);
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
    protected String getSign() {
        return " | ";
    }

    @Override
    protected int calculateOperation(int x, int y) {
        return x | y;
    }

    @Override
    protected BigDecimal calculateOperation(BigDecimal x, BigDecimal y) {
        throw new UnsupportedOperationException("BigDecimal OR unsupported");
    }
}
