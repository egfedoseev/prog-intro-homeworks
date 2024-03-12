package expression;

import java.math.BigDecimal;

public class Low extends UnaryOperation {
    public Low(AbstractExpression expression) {
        super(expression);
    }

    @Override
    protected String getSign() {
        return "low";
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
        int mn = 0;
        for (int i = binary.length() - 1; i >= 0 && binary.charAt(i) == '0'; --i) {
            ++mn;
        }
        return 1 << mn;
    }

    @Override
    protected BigDecimal calculateOperation(BigDecimal x) {
        throw new UnsupportedOperationException("BigDecimal Low unsupported");
    }
}
