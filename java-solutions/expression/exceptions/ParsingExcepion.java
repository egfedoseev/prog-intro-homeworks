package expression.exceptions;

public class ParsingExcepion extends Exception {
    public ParsingExcepion(String message) {
        super(message);
    }

    public ParsingExcepion(String message, Throwable cause) {
        super(message, cause);
    }
}
