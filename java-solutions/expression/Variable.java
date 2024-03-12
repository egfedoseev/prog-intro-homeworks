package expression;

import java.math.BigDecimal;

public class Variable implements AbstractExpression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public int evaluate(int val) {
        return val;
    }

    @Override
    public int evaluate(int x, int y, int z)  {
        return switch (name) {
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> throw new IllegalArgumentException("Incorrect variable name, expected: x|y|z, found: " + name);
        };
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String toMiniString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Variable && ((Variable) obj).name.equals(name);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public BigDecimal evaluate(BigDecimal x) {
        return x;
    }
}
