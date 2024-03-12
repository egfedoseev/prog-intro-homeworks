package wordstat;

public class PairIntString implements Comparable<PairIntString> {
    public final int first;
    public final String second;

    public PairIntString(int first, String second) {
        this.first = first;
        this.second = second;
    }

    public PairIntString() {
        first = 0;
        second = "";
    }

    @Override
    public int compareTo(PairIntString that) {
        return Integer.compare(first, that.first);
    }
}
