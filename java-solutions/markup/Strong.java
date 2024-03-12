package markup;

import java.util.List;

public class Strong extends MarkupElement {
    public Strong(List<Element> list) {
        super(list);
    }

    @Override
    protected String tagMarkdownOpen() {
        return "__";
    }

    @Override
    protected String tagBBCodeOpen() {
        return "[b]";
    }

    @Override
    protected String tagBBCodeClose() {
        return "[/b]";
    }

    @Override
    protected String tagHTMLOpen() {
        return "<strong>";
    }

    @Override
    protected String tagHTMLClose() {
        return "</strong>";
    }
}
