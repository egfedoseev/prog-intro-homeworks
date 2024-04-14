package scanner;

import java.io.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyScanner implements Iterator<String>, Closeable, AutoCloseable {
    private final BufferedReader reader;
    private int ch;

    public MyScanner(InputStream in) {
        reader = new BufferedReader(new InputStreamReader(in));
        ch = ' ';
    }

    private void nextChar() {
        try {
            ch = reader.read();
        } catch (IOException e) {
            System.err.println("Can't read character\nIOException: " + e.getMessage());
        }
    }

    private void skipWhitespace() {
        while (Character.isWhitespace(ch)) {
            nextChar();
        }
    }

    @Override
    public boolean hasNext() {
        skipWhitespace();
        return ch != -1;
    }

    @Override
    public String next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements");
        }

        StringBuilder sb = new StringBuilder();
        while (!Character.isWhitespace(ch)) {
            sb.append(ch);
            nextChar();
        }
        return sb.toString();
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

    public boolean hasNextLine() {
        return hasNext();
    }

    public String nextLine() {
        if (!hasNextLine()) {
            throw new NoSuchElementException("No more elements");
        }

        StringBuilder sb = new StringBuilder();
        while (ch != -1 && ch != '\n') {
            sb.append(ch);
            nextChar();
        }
        return sb.toString();
    }
}