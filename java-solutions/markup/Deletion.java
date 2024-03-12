package markup;

import java.util.List;

public class Deletion extends MarkupElement {
    public Deletion(List<Element> list) {
        super(list);
    }

    @Override
    protected String tagMarkdownOpen() {
        return "}}";
    }

    protected String tagMarkdonwClose() {
        return "{{";
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
        return "<del>";
    }

    @Override
    protected String tagHTMLClose() {
        return "</del>";
    }

    @Override
    public void toMarkdown(StringBuilder s) {
        super.toMarkdown(s);
    }

    @Override
    public void toBBCode(StringBuilder s) {
        super.toBBCode(s);
    }

    @Override
    public void toHTML(StringBuilder s) {
        super.toHTML(s);
    }
}
