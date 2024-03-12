package reverse;

import scanner.MyScanner;

import java.io.IOException;
import java.util.Arrays;

public class ReverseSumHexDecAbc {
    static int[][] newLine(int[][] ints, int size, int[] vals) {
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

    static int[] pushBack(int[] ints, int size, int val) {
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

    static int parseInt(String s) {
        if (s.length() > 2 && s.charAt(0) == '0' && s.charAt(1) == 'x') {
            return Integer.parseUnsignedInt(s.substring(2), 16);
        }
        int ret = 0;
        for (int i = 0; i < s.length(); ++i) {
            ret *= 10;
            if (Character.isLowerCase(s.charAt(i))) {
                ret += s.charAt(i) - 'a';
            } else {
                ret += s.charAt(i) - '0';
            }
        }
        return ret;
    }

    static String intToAbc(int x) {
        StringBuilder s = new StringBuilder();
        if (x == 0) {
            return "a";
        }
        boolean f = false;
        if (x < 0) {
            f = true;
            x *= -1;
        }
        while (x > 0) {
            s.append((char) (x % 10 + 'a'));
            x /= 10;
        }
        if (f) {
            s.append('-');
        }
        return s.reverse().toString();
    }

    public static void main(String[] args) {
        int[][] ints = new int[1][1];
        int[] elemsCnt = new int[1];
        int elemsCntSize = 0;

        int cntLine = 0;
        int mx = 0;
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
                        String t;
                        if (scanned.charAt(0) == '-') {
                            t = scanned.substring(1);
                        } else {
                            t = scanned;
                        }
                        t = t.toLowerCase();
                        ints[cntLine] = pushBack(ints[cntLine], cnt++, parseInt(t));
                        if (scanned.charAt(0) == '-') {
                            ints[cntLine][cnt - 1] *= -1;
                        }
                    }
                    prev = i;
                }

                mx = Integer.max(mx, ints[cntLine].length);
                ints = newLine(ints, ++cntLine, new int[1]);
                elemsCnt = pushBack(elemsCnt, elemsCntSize++, cnt);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        int[][] sum = new int[cntLine][];
        int[] sumCurColumn = new int[mx];

        for (int i = 0; i < cntLine; ++i) {
            sum[i] = new int[elemsCnt[i]];
            for (int j = 0; j < sum[i].length; ++j) {
                sumCurColumn[j] += ints[i][j];
                sum[i][j] = sumCurColumn[j];
                if (j > 0) {
                    sum[i][j] += sum[i][j - 1];
                }
            }
        }

        for (int i = 0; i < cntLine; ++i) {
            for (int j = 0; j < sum[i].length; ++j) {
                System.out.print(intToAbc(sum[i][j]));
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}