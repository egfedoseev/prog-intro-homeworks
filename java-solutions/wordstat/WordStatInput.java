package wordstat;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class WordStatInput {
    private static boolean isLetter(char c) {
        return Character.isLetter(c) || Character.getType(c) == Character.DASH_PUNCTUATION || c == '\'';
    }

    private static void putInDictionary(String s, Map<String, Integer> mp, int add) {
        mp.merge(s, add, Integer::sum);
    }

    private static void putWord(String word, Map<String, Integer> d, Map<String, Integer> met) {
        putInDictionary(word, d, 1);
        putInDictionary(word, met, 0);
    }

    public static void main(String[] args) {
        StringBuilder s = new StringBuilder();
        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(args[0]), StandardCharsets.UTF_8
                        )
                );
        ) {
            int ch = reader.read();
            while (ch >= 0) {
                s.append((char) ch);
                ch = reader.read();
            }
        } catch (IOException e) {
            System.err.println("Can't read, IO Excepption: " + e.getMessage());
            return;
        }

        Map<String, Integer> dictionary = new HashMap<>();
        Map<String, Integer> met = new HashMap<>();

        int previous = -1;
        for (int i = 0; i < s.length(); ++i) {
            if (!isLetter(s.charAt(i)) || i == s.length() - 1) {
                if (previous + 1 < i) {
                    putWord(s.substring(previous + 1, i).toLowerCase(), dictionary, met);
                }
                previous = i;
            }
        }

        try (
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(args[1]),
                                "utf8"
                        )
                )
        ) {
            previous = -1;
            for (int i = 0; i < s.length(); ++i) {
                if (isLetter(s.charAt(i)) && i != s.length() - 1) {
                    continue;
                }
                if (previous + 1 < i) {
                    String word = s.substring(previous + 1, i).toLowerCase();
                    if (met.get(word) == 0) {
                        writer.write(word + ' ' + dictionary.get(word) + System.lineSeparator());
                        met.put(word, 1);
                    }
                }
                previous = i;
            }
        } catch (IOException e) {
            System.err.println("Can't write, IO Exception " + e.getMessage());
        }
    }
}