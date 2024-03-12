package expression;

import java.math.BigDecimal;

public class High extends UnaryOperation {
    public High(AbstractExpression expression) {
        super(expression);
    }

    @Override
    protected String getSign() {
        return "high";
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public int calculateOperation(int x) {
        if (x == 0) {
            return 0;
        }
        String binary = Integer.toBinaryString(x);
        return 1 << (binary.length() - 1);
    }

    @Override
    protected BigDecimal calculateOperation(BigDecimal x) {
        throw new UnsupportedOperationException("BigDecimal High unsupported");
    }
}
