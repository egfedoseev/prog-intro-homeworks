package expression;

import java.math.BigDecimal;

public interface BigDecimalExpression extends ToMiniString {
    BigDecimal evaluate(BigDecimal x);
}
