package expression;

import java.math.BigDecimal;
import java.util.Objects;

public class Const implements AbstractExpression {
    private final Number val;

    public Const(int val) {
        this.val = val;
    }

    public Const(BigDecimal val) {
        this.val = val;
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public int evaluate(int val) {
        return this.val.intValue();
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return val.intValue();
    }

    @Override
    public BigDecimal evaluate(BigDecimal x) {
        return (BigDecimal) val;
    }

    @Override
    public String toString() {
        return val.toString();
    }

    @Override
    public String toMiniString() {
        return String.valueOf(val);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == Const.class && Objects.equals(((Const) obj).val, val);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
