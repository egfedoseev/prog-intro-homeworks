package markup;

import java.util.List;

public class OrderedList extends MarkableList {
    public OrderedList(List<ListItem> list) {
        super(list);
    }

    @Override
    protected String tagBBCodeOpen() {
        return "[list=1]";
    }
}
