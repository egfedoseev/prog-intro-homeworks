package reverse;

import scanner.MyScanner;

import java.io.IOException;
import java.util.Arrays;

public class Reverse {
    private static int[][] newLine(int[][] ints, int size, int[] vals) {
        int curLength = ints.length;
        while (size >= curLength) {
            curLength *= 2;
        }
        if (curLength > ints.length) {
            ints = Arrays.copyOf(ints, curLength);
        }
        ints[size] = Arrays.copyOf(vals, vals.length);
        return ints;
    }

    private static int[] pushBack(int[] ints, int size, int val) {
        int curLength = ints.length;
        while (size >= curLength) {
            curLength *= 2;
        }
        if (curLength > ints.length) {
            ints = Arrays.copyOf(ints, curLength);
        }
        ints[size] = val;
        return ints;
    }

    public static void main(String[] args) {
        int[][] ints = new int[1][1];
        int[] elemsCnt = new int[1];
        int elemsCntSize = 0;

        int cntLine = 0;

        try (MyScanner sc = new MyScanner(System.in)) {
            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                int cnt = 0;
                int prev = -1;
                for (int i = 0; i < s.length(); ++i) {
                    if (!Character.isWhitespace(s.charAt(i))) {
                        continue;
                    }
                    if (prev + 1 < i) {
                        String scanned = s.substring(prev + 1, i);
                        ints[cntLine] = pushBack(ints[cntLine], cnt++, Integer.parseInt(scanned));
                    }
                    prev = i;
                }

                ints = newLine(ints, ++cntLine, new int[1]);
                elemsCnt = pushBack(elemsCnt, elemsCntSize++, cnt);
            }
        } catch (IOException e) {
            System.out.println("Can't read, IOException: " + e.getMessage());
            return;
        }

        for (int i = cntLine - 1; i >= 0; --i) {
            for (int j = elemsCnt[i] - 1; j >= 0; --j) {
                System.out.print(ints[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}