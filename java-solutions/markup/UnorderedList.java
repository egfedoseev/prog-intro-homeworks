package markup;

import java.util.List;

public class UnorderedList extends MarkableList {
    public UnorderedList(List<ListItem> list) {
        super(list);
    }

    @Override
    protected String tagBBCodeOpen() {
        return "[list]";
    }
}
