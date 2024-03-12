package markup;

import java.util.List;

public class Code extends MarkupElement {
    public Code(List<Element> list) {
        super(list);
    }

    @Override
    protected String tagMarkdownOpen() {
        return "`";
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
        return "<code>";
    }

    @Override
    protected String tagHTMLClose() {
        return "</code>";
    }
}
