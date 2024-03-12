package game;

import java.util.Map;

public class MNKPosition implements Position {
    protected static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.',
            Cell.NOT_A_CELL, ' '
    );
    protected final Cell[][] cells;
    protected final Cell turn;

    protected final int m;
    protected final int n;

    private final int columnWidth;
    private final int rowOffset;


    public MNKPosition(Cell[][] cells, Cell turn) {
        this.cells = cells;
        this.turn = turn;
        m = cells.length;
        n = cells[0].length;

        columnWidth = String.valueOf(n).length();
        rowOffset = String.valueOf(m).length();
    }

    @Override
    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < m
                && 0 <= move.getColumn() && move.getColumn() < n
                && cells[move.getRow()][move.getColumn()] == Cell.E
                && move.getValue() == turn;
    }

    @Override
    public Cell getCell(int r, int c) {
        return cells[r][c];
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    @Override
    public String toString() {

        final StringBuilder sb = new StringBuilder();

        final StringBuilder columnNumberFormat = new StringBuilder(" %");
        columnNumberFormat.append(columnWidth);
        columnNumberFormat.append('s');

        final StringBuilder rowNumberFormat = new StringBuilder("%");
        rowNumberFormat.append(rowOffset);
        rowNumberFormat.append('d');
        rowNumberFormat.append('|');

        sb.append(" ".repeat(rowOffset));
        sb.append('|');
        for (int i = 0; i < n; ++i) {
            sb.append(String.format(columnNumberFormat.toString(), i));
        }
        sb.append('\n');
        sb.append("-".repeat(rowOffset + (columnWidth + 1) * n + 1));

        for (int r = 0; r < m; r++) {
            sb.append(System.lineSeparator());
            sb.append(String.format(rowNumberFormat.toString(), r));
            for (int c = 0; c < n; c++) {
                sb.append(String.format(columnNumberFormat.toString(), SYMBOLS.get(cells[r][c])));
            }
        }
        return sb.toString();
    }
}
