package game;

import java.util.Arrays;

public class MNKBoard implements Board {
    protected final Cell[][] cells;
    private Cell turn;

    private final int m;
    private final int n;
    private final int k;

    private int empty;

    public MNKBoard(int m, int n, int k) {
        this.m = m;
        this.n = n;
        this.k = k;
        this.cells = new Cell[m][n];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        empty = m * n;
        turn = Cell.X;
    }


    @Override
    public Position
    getPosition() {
        return new MNKPosition(cells, getCell());
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    @Override
    public boolean isTechnicalDraw() {
        final int[] moves = new int[]{
                0, 1,
                1, 0,
                1, 1,
                1, -1,
                -1, 1,
                -1, -1
        };

        for (int i = 0; i < moves.length / 2; ++i) {
            int lx = moves[i * 2] >= 0 ? 0 : n - 1;
            int ly = moves[i * 2 + 1] >= 0 ? 0 : m - 1;
            int cntO = 0;
            int cntE = 0;
            int cntX = 0;
            for (int x = lx, y = ly;
                 0 <= x && x < n && 0 <= y && y < m;
                 x += moves[i * 2], y += moves[i * 2 + 1]) {
                switch (cells[x][y]) {
                    case E:
                        ++cntE;
                        break;
                    case X:
                        ++cntX;
                        break;
                    case O:
                        ++cntO;
                        break;
                    default:
                        break;
                }
                while (cntO > 0 && cntX > 0) {
                    switch (cells[lx][ly]) {
                        case E:
                            --cntE;
                            break;
                        case X:
                            --cntX;
                            break;
                        case O:
                            --cntO;
                            break;
                    }
                    lx += moves[i * 2];
                    ly += moves[i * 2 + 1];
                }
                if (cntX + cntE + cntO >= k) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!getPosition().isValid(move)) {
            return Result.LOSE;
        }

        final int row = move.getRow();
        final int column = move.getColumn();

        cells[row][column] = move.getValue();
        --empty;

        int inColumn = 1;
        for (int i = row + 1; i < m && cells[i][column] == turn && i - row < k; ++i) {
            ++inColumn;
        }
        for (int i = row - 1; i >= 0 && cells[i][column] == turn && row - i < k; --i) {
            ++inColumn;
        }

        int inRow = 1;
        for (int i = column + 1; i < n && cells[row][i] == turn && i - column < k; ++i) {
            ++inRow;
        }
        for (int i = column - 1; i >= 0 && cells[row][i] == turn && column - i < k; --i) {
            ++inRow;
        }

        int inDiag1 = 1;
        for (int i = 1; row + i < m && column + i < n && cells[row + i][column + i] == turn && i < k; ++i) {
            ++inDiag1;
        }
        for (int i = -1; row + i >= 0 && column + i >= 0 && cells[row + i][column + i] == turn && -i < k; --i) {
            ++inDiag1;
        }

        int inDiag2 = 1;
        for (int i = 1; row + i < m && column - i >= 0 && cells[row + i][column - i] == turn && i < k; ++i) {
            ++inDiag2;
        }
        for (int i = -1; row + i >= 0 && column - i < n && cells[row + i][column - i] == turn && -i < k; --i) {
            ++inDiag2;
        }

        if (inRow >= k || inColumn >= k || inDiag1 >= k || inDiag2 >= k) {
            return Result.WIN;
        }

        if (empty == 0 || isTechnicalDraw()) {
            return Result.DRAW;
        }

        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }
}
