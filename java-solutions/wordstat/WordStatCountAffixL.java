package wordstat;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.lang.StringBuilder;

public class WordStatCountAffixL {
    static boolean isLetter(char c) {
        return Character.isLetter(c) || Character.getType(c) == Character.DASH_PUNCTUATION || c == '\'';
    }

    static void putInDictionary(String s, Map<String, Integer> mp, int add) {
        mp.merge(s, add, Integer::sum);
    }

    static void putAffix(String s, Map<String, Integer> d, Map<String, Integer> met) {
        String prefix = s.substring(0, 2);
        String suffix = s.substring(s.length() - 2);
        putInDictionary(prefix, d, 1);
        putInDictionary(suffix, d, 1);
        putInDictionary(prefix, met, 0);
        putInDictionary(suffix, met, 0);
    }

    static PairIntString makePair(String s, Map<String, Integer> d, Map<String, Integer> m) {
        int cnt = d.get(s);
        int met = m.get(s);
        putInDictionary(s, m, 1);
        if (met == 0) {
            return new PairIntString(cnt, s);
        }
        return new PairIntString();
    }

    public static void main(String[] args) {
        StringBuilder s = new StringBuilder();
        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(args[0]),
                                StandardCharsets.UTF_8
                        )
                )
        ) {
            int ch = reader.read();
            while (ch >= 0) {
                s.append((char) ch);
                ch = reader.read();
            }
        } catch (IOException e) {
            System.err.println("IO Excepption: " + e.getMessage());
            return;
        }

        Map<String, Integer> dictionary = new HashMap<>();
        Map<String, Integer> met = new HashMap<>();

        int previous = -1;
        for (int i = 0; i < s.length(); ++i) {
            if (!isLetter(s.charAt(i)) || i == s.length() - 1) {
                if (previous + 3 <= i) {
                    putAffix(s.substring(previous + 1, i).toLowerCase(), dictionary, met);
                }
                previous = i;
            }
        }
        try (
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(args[1]),
                                StandardCharsets.UTF_8
                        )
                )
        ) {
            PairIntString[] pairs = new PairIntString[dictionary.size()];
            int cntPairs = 0;
            previous = -1;
            for (int i = 0; i < s.length(); ++i) {
                if (isLetter(s.charAt(i)) && i != s.length() - 1) {
                    continue;
                }
                if (previous + 3 <= i) {
                    String prefix = s.substring(previous + 1, previous + 3).toLowerCase();
                    String suffix = s.substring(i - 2, i).toLowerCase();

                    PairIntString prefPair = makePair(prefix, dictionary, met);
                    PairIntString suffPair = makePair(suffix, dictionary, met);
                    if (prefPair.first != 0) {
                        pairs[cntPairs++] = prefPair;
                    }
                    if (suffPair.first != 0) {
                        pairs[cntPairs++] = suffPair;
                    }
                }
                previous = i;
            }
            Arrays.sort(pairs);
            for (PairIntString pair : pairs) {
                writer.write(pair.second + ' ' + pair.first + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Can't write, IO Exception " + e.getMessage());
        }
    }
}