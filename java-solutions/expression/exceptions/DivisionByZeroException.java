package expression.exceptions;

public class DivisionByZeroException extends EvaluationException {
    public DivisionByZeroException(String message) {
        super(message);
    }

    public DivisionByZeroException(Number left, Number right, String sign) {
        super("DivisionByZero", left, right, sign);
    }

    public DivisionByZeroException(Number left, Number right, String sign, Throwable cause) {
        super("DivisionByZero", left, right, sign, cause);
    }

    public DivisionByZeroException(String sign, Number val) {
        super("DivisionByZero", sign, val);
    }

    public DivisionByZeroException(String sign, Number val, Throwable cause) {
        super("DivisionByZero", sign, val, cause);
    }
}
