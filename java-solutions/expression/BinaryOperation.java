package expression;

import java.math.BigDecimal;

public abstract class BinaryOperation implements AbstractExpression {

    protected final AbstractExpression first;

    protected final AbstractExpression second;

    public BinaryOperation(AbstractExpression first, AbstractExpression second) {
        this.first = first;
        this.second = second;
    }

    protected abstract String getSign();

    protected abstract boolean isCommutative();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        sb.append(first.toString());
        sb.append(getSign());
        sb.append(second.toString());
        sb.append(')');
        return sb.toString();
    }

    @Override
    public String toMiniString() {
        StringBuilder sb = new StringBuilder();

        if (first.getPriority() >= getPriority()) {
            sb.append(first.toMiniString());
        } else {
            if (first instanceof BinaryOperation) {
                sb.append("(");
            }
            sb.append(first.toMiniString());
            if (first instanceof BinaryOperation) {
                sb.append(")");
            }
        }

        sb.append(getSign());

        if (getSign().equals(" * ") && second != null && second instanceof Divide) {
            sb.append("(");
            sb.append(second.toMiniString());
            sb.append(")");
        } else if ((second.getPriority() > getPriority() && !isCommutative()) ||
                (second.getPriority() >= getPriority() && isCommutative())) {
            sb.append(second.toMiniString());
        } else {
            if (second instanceof BinaryOperation) {
                sb.append("(");
            }
            sb.append(second.toMiniString());
            if (second instanceof BinaryOperation) {
                sb.append(")");
            }
        }

        return sb.toString();
    }

    protected abstract int calculateOperation(int x, int y);

    protected abstract BigDecimal calculateOperation(BigDecimal x, BigDecimal y);

    @Override
    public int evaluate(int x) {
        return calculateOperation(first.evaluate(x), second.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return calculateOperation(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }

    @Override
    public BigDecimal evaluate(BigDecimal x) {
        return calculateOperation(first.evaluate(x), second.evaluate(x));
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.getClass().equals(getClass()) &&
                first.equals(((BinaryOperation) obj).first) && second.equals(((BinaryOperation) obj).second);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
