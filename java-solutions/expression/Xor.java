package expression;

import java.math.BigDecimal;

public class Xor extends BinaryOperation {
    public Xor(AbstractExpression first, AbstractExpression second) {
        super(first, second);
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    protected boolean isCommutative() {
        return true;
    }

    @Override
    protected String getSign() {
        return " ^ ";
    }

    @Override
    protected int calculateOperation(int x, int y) {
        return x ^ y;
    }

    @Override
    protected BigDecimal calculateOperation(BigDecimal x, BigDecimal y) {
        throw new UnsupportedOperationException("BigDecimal XOR unsupported");
    }
}
