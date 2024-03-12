package expression;

import java.math.BigDecimal;

public class UnaryT1 extends UnaryOperation {
    public UnaryT1(AbstractExpression expression) {
        super(expression);
    }

    @Override
    protected String getSign() {
        return "t1";
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    protected int calculateOperation(int x) {
        String binary = Integer.toBinaryString(x);
        int cnt = 0;
        for (int i = binary.length() - 1; i >= 0 && binary.charAt(i) == '1'; --i) {
            ++cnt;
        }
        return cnt;
    }

    @Override
    public BigDecimal calculateOperation(BigDecimal x) {
        throw new UnsupportedOperationException("BigDecimal T1 unsupported");
    }
}
