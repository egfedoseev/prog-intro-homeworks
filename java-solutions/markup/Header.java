package markup;

import java.util.List;

public class Header extends Paragraph {
    private final int level;

    public Header(List<Element> list, int level) {
        super(list);
        this.level = level;
    }

    @Override
    protected String tagHTMLOpen() {
        return "<h" + level + ">";
    }

    @Override
    protected String tagHTMLClose() {
        return "</h" + level + ">";
    }
}
