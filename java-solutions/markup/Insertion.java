package markup;

import java.util.List;

public class Insertion extends MarkupElement {
    public Insertion(List<Element> list) {
        super(list);
    }

    @Override
    protected String tagMarkdownOpen() {
        return "<<";
    }

    @Override
    protected String tagMarkdownClose() {
        return ">>";
    }

    @Override
    protected String tagBBCodeOpen() {
        return "";
    }

    @Override
    protected String tagBBCodeClose() {
        return "";
    }

    @Override
    protected String tagHTMLOpen() {
        return "<ins>";
    }

    @Override
    protected String tagHTMLClose() {
        return "</ins>";
    }
}
