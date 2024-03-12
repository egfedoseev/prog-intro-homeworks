package expression.exceptions;

public class OverflowException extends EvaluationException {
    public OverflowException(String message) {
        super(message);
    }

    public OverflowException(Number left, Number right, String sign) {
        super("Overflow", left, right, sign);
    }

    public OverflowException(Number left, Number right, String sign, Throwable cause) {
        super("Overflow", left, right, sign, cause);
    }

    public OverflowException(String sign, Number val) {
        super("Overflow", sign, val);
    }

    public OverflowException(String sign, Number val, Throwable cause) {
        super("Overflow", sign, val, cause);
    }
}
