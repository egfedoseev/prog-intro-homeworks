package game;

public class CircleBoard extends MNKBoard {
    public CircleBoard(int d, int k) {
        super(d, d, k);
        for (int r = 0; r < d; ++r) {
            for (int c = 0; c < d; ++c) {
                int radius = d / 2;
                int x;
                int y;
                if (d % 2 == 0) {
                    if (r < radius) {
                        y = r - radius + 1;
                    } else {
                        y = r - radius;
                    }
                    if (c < radius) {
                        x = c - radius + 1;
                    } else {
                        x = c - radius;
                    }
                } else {
                    y = r - radius;
                    x = c - radius;
                }
                if (x * x + y * y > radius * radius) {
                    cells[r][c] = Cell.NOT_A_CELL;
                }
            }
        }
    }

    @Override
    public Result makeMove(Move move) {
        return super.makeMove(move);
    }
}
