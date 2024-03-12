package wspp;

public class PairIntListString implements Comparable<PairIntListString> {
    public IntList first;
    public String second;

    PairIntListString(IntList first, String second) {
        this.first = first;
        this.second = second;
    }

    PairIntListString() {
        first = new IntList();
        second = "";
    }

    @Override
    public int compareTo(PairIntListString that) {
        StringBuilder s1 = new StringBuilder(second);
        s1.reverse();
        StringBuilder s2 = new StringBuilder(that.second);
        s2.reverse();
        return s1.compareTo(s2);
    }
}