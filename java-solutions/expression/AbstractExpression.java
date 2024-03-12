package expression;

public interface AbstractExpression extends Expression, TripleExpression, BigDecimalExpression {
    int getPriority();
}
