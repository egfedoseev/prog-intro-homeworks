package wspp;

import scanner.MyScanner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Wspp {
    static boolean isLetter(char c) {
        return Character.isLetter(c) || Character.getType(c) == Character.DASH_PUNCTUATION || c == '\'';
    }

    static void putInDictionary(String s, Map<String, Integer> mp, int val) {
        mp.merge(s, val, Integer::sum);
    }

    static void putWord(String s, Map<String, IntList> d, Map<String, Integer> m, int pos) {
        putInDictionary(s, m, 0);
        d.computeIfAbsent(s, k -> new IntList());
        IntList gt = d.get(s);
        gt.pushBack(pos);
    }

    static PairIntListString makePair(String s, Map<String, IntList> d, Map<String, Integer> m) {
        IntList cnt = d.get(s);
        int met = m.get(s);
        putInDictionary(s, m, 1);
        if (met == 0) {
            return new PairIntListString(cnt, s);
        }
        return new PairIntListString();
    }

    public static void main(String[] args) {
        StringBuilder s = new StringBuilder();
        try (MyScanner scanner = new MyScanner(new File(args[0]))) {
            while (scanner.hasNext()) {
                s.append(scanner.next());
            }
        } catch (IOException e) {
            System.err.println("Can't read, IO Exception: " + e.getMessage());
            return;
        }

        Map<String, IntList> d = new HashMap<>();
        Map<String, Integer> met = new HashMap<>();

        int previous = -1;
        int cntAll = 0;
        for (int i = 0; i < s.length(); ++i) {
            if (isLetter(s.charAt(i)) && i != s.length() - 1) {
                continue;
            }
            int r = i;
            if (isLetter(s.charAt(i))) {
                ++r;
            }
            if (previous + 1 < r) {
                String word = s.substring(previous + 1, r).toLowerCase();
                putWord(word, d, met, ++cntAll);
            }
            previous = i;
        }

        try (
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(args[1]),
                                StandardCharsets.UTF_8
                        )
                )
        ) {
            PairIntListString[] pairs = new PairIntListString[d.size()];
            int cntPairs = 0;

            previous = -1;
            for (int i = 0; i < s.length() && cntPairs < pairs.length; ++i) {
                if (isLetter(s.charAt(i)) && i != s.length() - 1) {
                    continue;
                }
                int r = i;
                if (isLetter(s.charAt(i))) {
                    ++r;
                }
                if (previous + 1 < r) {
                    String word = s.substring(previous + 1, r).toLowerCase();
                    pairs[cntPairs++] = makePair(word, d, met);
                    if (Objects.equals(pairs[cntPairs - 1].second, "")) {
                        --cntPairs;
                    }
                }
                previous = i;
            }

            for (PairIntListString pair : pairs) {
                writer.write(pair.second + " " + pair.first.size());
                for (int i = 0; i < pair.first.size(); ++i) {
                    writer.write(" " + pair.first.get(i));
                }
                writer.write(System.lineSeparator());
            }

        } catch (FileNotFoundException e) {
            System.out.println("File " + args[1] + " not found");
        } catch (IOException e) {
            System.err.println("Can't write, IO Exception " + e.getMessage());
        }
    }
}