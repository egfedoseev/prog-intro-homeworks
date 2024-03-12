package scanner;

import java.io.*;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

public class MyScanner implements AutoCloseable {
    private final Reader reader;
    private char[] buff = new char[buffSize];
    private int cur = 0;
    private int cnt = 0;
    private static final int buffSize = 8192;
    private int nextPos = 0;

    public MyScanner(File file) throws FileNotFoundException {
        reader = new FileReader(file);
    }

    public MyScanner(String str) {
        reader = new StringReader(str);
    }

    public MyScanner(InputStream in) {
        reader = new InputStreamReader(in);
    }

    private void updateBuffer() throws IOException {
        buff = new char[buffSize];
        cnt = reader.read(buff);
        cur = 0;
        nextPos = 0;
    }

    private void addBuffer() throws IOException {
        char[] newBuff = new char[buffSize];
        int newCnt = reader.read(newBuff);
        if (newCnt <= 0) {
            return;
        }
        int prevLength = buff.length;
        buff = Arrays.copyOf(buff, buff.length + buffSize);
        System.arraycopy(newBuff, 0, buff, prevLength, newCnt);
        cnt += newCnt;
    }

    public boolean hasNext() throws IOException {
        if (cur == cnt) {
            updateBuffer();
        }
        int curTemp = cur;

        if (nextPos > cur && nextPos < cnt) {
            return true;
        }

        while (curTemp < cnt && buff[curTemp] == ' ') {
            ++curTemp;
            if (curTemp == cnt) {
                addBuffer();
            }
        }
        nextPos = curTemp;
        return nextPos < cnt;
    }

    public String next() throws IOException {
        StringBuilder s = new StringBuilder();

        if (cur == cnt) {
            updateBuffer();
        }

        cur = nextPos;

        while (cur < cnt) {
            if (addCharToStringBuilder(s)) {
                break;
            }
            if (s.charAt(s.length() - 1) == ' ') {
                break;
            }
        }
        nextPos = cur;
        return s.toString();
    }

    public int nextInt() throws NoSuchElementException, IOException {
        if (!hasNext()) {
            throw new NoSuchElementException("No elements in input");
        }
        return Integer.parseInt(next());
    }

    public boolean hasNextLine() throws IOException {
        if (cur == cnt) {
            updateBuffer();
        }
        return cur < cnt;
    }

    public String nextLine() throws IOException {
        StringBuilder s = new StringBuilder();
        if (cur == cnt) {
            updateBuffer();
        }

        while (cur < cnt) {
            if (addCharToStringBuilder(s)) break;
        }
        return s.toString();
    }

    private boolean addCharToStringBuilder(StringBuilder s) throws IOException {
        s.append(buff[cur]);
        ++cur;
        if (cur == cnt) {
            updateBuffer();
        }

        return s.length() >= System.lineSeparator().length() &&
                Objects.equals(s.substring(s.length() - System.lineSeparator().length()), System.lineSeparator());
    }

    public void close() throws IOException {
        reader.close();
        buff = new char[buffSize];
        cnt = 0;
        cur = 0;
    }
}