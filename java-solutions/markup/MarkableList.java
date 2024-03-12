package markup;

import java.util.List;

public abstract class MarkableList extends ParagraphAndMarkableList {
    private final List<ListItem> list;

    protected abstract String tagBBCodeOpen();

    protected String tagBBCodeClose() {
        return "[/list]";
    }

    public MarkableList(List<ListItem> list) {
        this.list = List.copyOf(list);
    }

    @Override
    public void toBBCode(StringBuilder s) {
        s.append(tagBBCodeOpen());
        for (ListItem item : list) {
            item.toBBCode(s);
        }
        s.append(tagBBCodeClose());
    }
}
