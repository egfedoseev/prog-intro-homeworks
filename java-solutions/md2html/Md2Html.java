package md2html;

import markup.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Md2Html {
    private static final Map<String, String> TAGS = Map.of(
            "*", "*",
            "**", "**",
            "_", "_",
            "__", "__",
            "--", "--",
            "`", "`",
            "<<", ">>",
            "}}", "{{"
    );

    static void addText(String s, int left, int right, ArrayList<Element> elems) {
        if (left < right) {
            elems.add(new Text(s.substring(left, right)));
        }
    }

    static void addElement(String s, String tag, int prev, int cur, int parsedUntil,
                           ArrayList<Element> elems) {
        if (prev >= tag.length() && prev < cur) {
            addText(s, parsedUntil + 1, prev - tag.length(), elems);
            Element elem = switch (tag) {
                case "*", "_" -> new Emphasis(parseElements(s, prev, cur));
                case "--" -> new Strikeout(parseElements(s, prev, cur));
                case "**", "__" -> new Strong(parseElements(s, prev, cur));
                case "`" -> new Code(parseElements(s, prev, cur));
                case "<<" -> new Insertion(parseElements(s, prev, cur));
                case "}}" -> new Deletion(parseElements(s, prev, cur));
                default -> null;
            };
            elems.add(elem);
        }
    }

    static boolean isTag(String s) {
        return TAGS.containsKey(s);
    }

    static ArrayList<Element> parseElements(String s, int left, int right) {
        ArrayList<Element> ret = new ArrayList<>();

        int parsedUntil = left - 1;

        for (int i = left; i < right; ++i) {
            String lastTwoElems = null;
            String lastElem = String.valueOf(s.charAt(i));
            int prevSize = ret.size();
            boolean shield = false;
            if (i > left && s.charAt(i - 1) == '\\') {
                continue;
            }
            if (i - 1 > left && s.charAt(i - 2) == '\\') {
                shield = true;
            }
            if (i > left) {
                lastTwoElems = s.substring(i - 1, i + 1);
            }

            String openTag = null;
            String closeTag = null;
            boolean foundTag = false;

            if (i > left && isTag(lastTwoElems) && !shield) {
                openTag = lastTwoElems;
                closeTag = TAGS.get(openTag);
                foundTag = true;
            } else {
                String nextElem = "";
                if (i + 1 < right) {
                    nextElem = String.valueOf(s.charAt(i + 1));
                }
                if (isTag(lastElem) && i + 1 < right && !(lastElem.equals("*") && nextElem.equals("*")) &&
                        !(lastElem.equals("_") && nextElem.equals("_"))) {
                    openTag = lastElem;
                    closeTag = lastElem;
                    foundTag = true;
                }
            }
            if (foundTag) {
                int next = s.indexOf(closeTag, i + closeTag.length() + 1);
                boolean isEmphasis = closeTag.equals("*") || closeTag.equals("_");
                while (next != -1 && next < right && (s.charAt(next - 1) == '\\' ||
                        (isEmphasis && next + 1 < right &&
                                (s.charAt(next + 1) == s.charAt(next) || s.charAt(next - 1) == s.charAt(next))))) {
                    next = s.indexOf(closeTag, next + closeTag.length() + 1);
                }
                if (next != -1 && s.charAt(next - 1) != '\\' && next < right) {
                    addElement(s, openTag, i + 1, next, parsedUntil, ret);
                    i = next + closeTag.length() - 1;
                }
            }

            if (prevSize < ret.size()) {
                parsedUntil = i;
            }
        }

        addText(s, parsedUntil + 1, right, ret);

        return ret;
    }

    static void addParagraph(ArrayList<Element> elems, ArrayList<Paragraph> paragraphs, int level) {
        Paragraph paragraph;
        if (!elems.isEmpty()) {
            if (level == 0) {
                paragraph = new Paragraph(elems);
            } else {
                paragraph = new Header(elems, level);
            }
            paragraphs.add(paragraph);
        }
    }

    static ArrayList<Paragraph> parseParagraphsAndHeaders(String s) {
        ArrayList<Paragraph> paragraphs = new ArrayList<>();

        ArrayList<Element> curElements;
        int level;
        String doubleSeparator = System.lineSeparator() + System.lineSeparator();

        for (int i = 0; i < s.length(); ++i) {
            level = 0;

            int left = i;
            while (left + System.lineSeparator().length() - 1 < s.length() &&
                    s.startsWith(System.lineSeparator(), left)) {
                left += System.lineSeparator().length();
            }
            int right = s.indexOf(doubleSeparator, left);

            if (right == -1) {
                right = s.length();
            }

            while (left < s.length() && s.charAt(left) == '#') {
                ++level;
                ++left;
            }
            if (level > 0 && Character.isWhitespace(s.charAt(left))) {
                ++left;
            } else {
                left -= level;
                level = 0;
            }

            curElements = parseElements(s, left, right);

            i = right + doubleSeparator.length() - 1;
            addParagraph(curElements, paragraphs, level);
        }

        return paragraphs;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Incorrect number of arguments. Expected 2, usage: INPUT.in OUTPUT.html");
            return;
        }

        StringBuilder input = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            int c = reader.read();
            while (c >= 0) {
                input.append((char) c);
                c = reader.read();
            }

            if (input.length() >= System.lineSeparator().length() &&
                    input.substring(input.length() - System.lineSeparator().length()).equals(System.lineSeparator())) {
                input.delete(input.length() - System.lineSeparator().length(), input.length());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Input file " + args[0] + " not found" + System.lineSeparator() +
                    "FileNotFoundException: " + e.getMessage());
            return;
        } catch (IOException e) {
            System.out.println("Can't read, IOException: " + e.getMessage());
            return;
        }


        List<Paragraph> elems = parseParagraphsAndHeaders(input.toString());

        StringBuilder output = new StringBuilder();
        for (Paragraph elem : elems) {
            elem.toHTML(output);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]))) {
            writer.write(output.toString());
        } catch (FileNotFoundException e) {
            System.out.println("Output file " + args[1] + " not found" + System.lineSeparator() +
                    "FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Can't write, IOException: " + e.getMessage());
        }
    }
}
