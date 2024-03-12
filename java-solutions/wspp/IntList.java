package wspp;

import java.util.Arrays;

public class IntList {
    private int[] ints;
    private int size;

    IntList() {
        ints = new int[1];
        size = 0;
    }

    IntList(int[] ints) {
        this.ints = Arrays.copyOf(ints, Math.max(ints.length, 1));
        size = ints.length;
    }

    public void pushBack(int val) {
        int curLength = ints.length;
        while (size >= curLength) {
            curLength *= 2;
        }
        if (curLength > ints.length) {
            ints = Arrays.copyOf(ints, curLength);
        }
        ints[size++] = val;
    }

    public int back() {
        return ints[size - 1];
    }

    public int size() {
        return size;
    }

    public int get(int idx) throws IndexOutOfBoundsException {
        if (idx < 0 || idx >= size) {
            throw new IndexOutOfBoundsException(idx);
        }
        return ints[idx];
    }
}
