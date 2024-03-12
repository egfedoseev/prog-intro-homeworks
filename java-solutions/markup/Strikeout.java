package markup;

import java.util.List;

public class Strikeout extends MarkupElement {
    public Strikeout(List<Element> list) {
        super(list);
    }

    @Override
    protected String tagMarkdownOpen() {
        return "~";
    }

    @Override
    protected String tagBBCodeOpen() {
        return "[s]";
    }

    @Override
    protected String tagBBCodeClose() {
        return "[/s]";
    }

    @Override
    protected String tagHTMLOpen() {
        return "<s>";
    }

    @Override
    protected String tagHTMLClose() {
        return "</s>";
    }
}
