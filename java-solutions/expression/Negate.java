package expression;

import java.math.BigDecimal;

public class Negate extends UnaryOperation {
    public Negate(AbstractExpression expression) {
        super(expression);
    }

    @Override
    protected String getSign() {
        return "-";
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    protected int calculateOperation(int x) {
        return -x;
    }

    @Override
    protected BigDecimal calculateOperation(BigDecimal x) {
        return x.negate();
    }
}
