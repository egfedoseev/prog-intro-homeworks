package expression.parser;

public interface CharSource {
    boolean hasNext();

    char next();

    void setCheckPoint();

    void returnToCheckPoint();

    IllegalArgumentException error(String message);
}
