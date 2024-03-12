package markup;

import java.util.List;

public class Emphasis extends MarkupElement {
    public Emphasis(List<Element> list) {
        super(list);
    }

    @Override
    protected String tagMarkdownOpen() {
        return "*";
    }

    @Override
    protected String tagHTMLOpen() {
        return "<em>";
    }

    @Override
    protected String tagHTMLClose() {
        return "</em>";
    }

    @Override
    protected String tagBBCodeOpen() {
        return "[i]";
    }

    @Override
    protected String tagBBCodeClose() {
        return "[/i]";
    }
}
