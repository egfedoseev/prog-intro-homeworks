package markup;

import java.util.List;

public class ListItem implements BBCode {
    private final List<ParagraphAndMarkableList> list;

    protected String tagBBCodeOpen() {
        return "[*]";
    }

    public ListItem(List<ParagraphAndMarkableList> list) {
        this.list = List.copyOf(list);
    }

    @Override
    public void toBBCode(StringBuilder s) {
        s.append(tagBBCodeOpen());
        for (ParagraphAndMarkableList elem : list) {
            elem.toBBCode(s);
        }
    }
}
