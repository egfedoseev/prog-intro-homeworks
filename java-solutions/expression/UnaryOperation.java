package expression;

import java.math.BigDecimal;

public abstract class UnaryOperation implements AbstractExpression {
    protected final AbstractExpression expression;

    public UnaryOperation(AbstractExpression expression) {
        this.expression = expression;
    }

    protected abstract String getSign();

    protected abstract int calculateOperation(int x);

    protected abstract BigDecimal calculateOperation(BigDecimal x);

    @Override
    public int evaluate(int x) {
        return calculateOperation(expression.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return calculateOperation(expression.evaluate(x, y, z));
    }

    @Override
    public BigDecimal evaluate(BigDecimal x) {
        return calculateOperation(expression.evaluate(x));
    }

    @Override
    public String toString() {
        return getSign() +
                '(' +
                expression.toString() +
                ')';
    }

    @Override
    public String toMiniString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getSign());
        if (expression instanceof BinaryOperation) {
            sb.append("(");
        } else {
            sb.append(" ");
        }
        sb.append(expression.toMiniString());
        if (expression instanceof BinaryOperation) {
            sb.append(")");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.getClass().equals(getClass()) && ((UnaryOperation) obj).getSign().equals(getSign()) &&
                expression.equals(((UnaryOperation) obj).expression);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
