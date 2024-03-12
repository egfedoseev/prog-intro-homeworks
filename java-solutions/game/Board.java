package game;

public interface Board {
    Position getPosition();

    Cell getCell();

    boolean isTechnicalDraw();

    Result makeMove(Move move);
}
