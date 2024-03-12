package wspp;

import scanner.MyScanner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class WsppSortedRFirst {
    private static boolean isLetter(char c) {
        return Character.isLetter(c) || Character.getType(c) == Character.DASH_PUNCTUATION || c == '\'';
    }

    private static void putInDictionary(String s, Map<String, Integer> mp, int val) {
        mp.merge(s, val, Integer::sum);
    }

    private static void putWord(String s, Map<String, IntList> dictionary, Map<String, Integer> met,
                                Map<String, Integer> cnt, int pos, int prevLinePos) {
        putInDictionary(s, met, 0);
        putInDictionary(s, cnt, 1);
        IntList gt = dictionary.get(s);
        if (gt == null) {
            gt = new IntList();
        }
        if (gt.size() == 0 || gt.back() <= prevLinePos) {
            gt.pushBack(pos);
        }
        dictionary.put(s, gt);
    }

    private static int[] parseWords(String s, int prevLinePos, int cntAll, Map<String, IntList> dictionary,
                                    Map<String, Integer> met, Map<String, Integer> cnt) {
        int previous = -1;
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
                putWord(word, dictionary, met, cnt, ++cntAll, prevLinePos);
            }
            previous = i;
            if (i + 1 >= System.lineSeparator().length() && // NOTE: I think it's not ok to check line separators here. why it's not checked in scanner?
                    Objects.equals(s.substring(i + 1 - System.lineSeparator().length(), i + 1), System.lineSeparator())) {
                prevLinePos = cntAll;
            }
        }
        return new int[]{prevLinePos, cntAll};
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Excepted 2 arguments, found " + args.length + ". Usage: wspp INPUT OUTPUT");
            return;
        }

        Map<String, IntList> d = new TreeMap<>();
        Map<String, Integer> met = new TreeMap<>();
        Map<String, Integer> cnt = new TreeMap<>();
        int cntAll = 0;
        int prevLinePos = -1;

        try (MyScanner scanner = new MyScanner(new File(args[0]))) {
            while (scanner.hasNext()) {
                String s = scanner.next();
                int[] tmp = parseWords(s, prevLinePos, cntAll, d, met, cnt);
                prevLinePos = tmp[0];
                cntAll = tmp[1];
            }
        } catch (IOException e) {
            System.err.println("Can't read, IO Exception: " + e.getMessage());
            return;
        }

        PairIntListString[] pairs = new PairIntListString[d.size()];
        int cntPairs = 0;

        for (Map.Entry<String, IntList> entry : d.entrySet()) {
            String word = entry.getKey();
            IntList list = entry.getValue();
            pairs[cntPairs++] = new PairIntListString(list, word);
        }

        Arrays.sort(pairs);

        try (
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(args[1]),
                                StandardCharsets.UTF_8
                        )
                )
        ) {
            for (PairIntListString pair : pairs) {
                writer.write(pair.second + " " + cnt.get(pair.second));
                for (int i = 0; i < pair.first.size(); ++i) {
                    writer.write(" " + pair.first.get(i));
                }
                writer.write(System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File " + args[1] + " not found");
        } catch (IOException e) {
            System.err.println("Can't write, IO Exception: " + e.getMessage());
        }
    }
}