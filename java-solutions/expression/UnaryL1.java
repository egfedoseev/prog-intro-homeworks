package expression;

import java.math.BigDecimal;

public class UnaryL1 extends UnaryOperation {
    public UnaryL1(AbstractExpression expression) {
        super(expression);
    }

    @Override
    protected String getSign() {
        return "l1";
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    protected int calculateOperation(int x) {
        String binary = Integer.toBinaryString(x);
        if (binary.length() < 32) {
            return 0;
        }
        int cnt = 0;
        for (int i = 0; i < binary.length() && binary.charAt(i) == '1'; ++i) {
            ++cnt;
        }
        return cnt;
    }

    @Override
    protected BigDecimal calculateOperation(BigDecimal x) {
        throw new UnsupportedOperationException("BigDecimal L1 unsupported");
    }
}
