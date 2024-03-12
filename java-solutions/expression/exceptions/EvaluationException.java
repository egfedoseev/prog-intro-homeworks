package expression.exceptions;

public class EvaluationException extends RuntimeException {
    public EvaluationException(String message) {
        super(message);
    }

    protected EvaluationException(String message, Number left, Number right, String sign) {
        super(message + ": " + left + sign + right);
    }

    protected EvaluationException(String message, Number left, Number right, String sign, Throwable cause) {
        super(message + ": " + left + sign + right, cause);
    }

    protected EvaluationException(String message, String sign, Number val) {
        super(message + ": " + sign + val);
    }

    protected EvaluationException(String message, String sign, Number val, Throwable cause) {
        super(message + ": " + sign + val, cause);
    }
}
