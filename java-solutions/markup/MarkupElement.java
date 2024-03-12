package markup;

import java.util.List;

public abstract class MarkupElement extends Element {
    private final List<Element> list;

    public MarkupElement(List<Element> list) {
        this.list = List.copyOf(list);
    }

    abstract protected String tagMarkdownOpen();

    protected String tagMarkdownClose() {
        return tagMarkdownOpen();
    }

    protected abstract String tagBBCodeOpen();

    protected abstract String tagBBCodeClose();

    abstract protected String tagHTMLOpen();

    abstract protected String tagHTMLClose();

    @Override
    public void toMarkdown(StringBuilder s) {
        s.append(tagMarkdownOpen());
        for (Element elem : list) {
            elem.toMarkdown(s);
        }
        s.append(tagMarkdownOpen());
    }

    @Override
    public void toBBCode(StringBuilder s) {
        s.append(tagBBCodeOpen());
        for (Element elem : list) {
            elem.toBBCode(s);
        }
        s.append(tagBBCodeClose());
    }

    @Override
    public void toHTML(StringBuilder s) {
        s.append(tagHTMLOpen());
        for (Element elem : list) {
            elem.toHTML(s);
        }
        s.append(tagHTMLClose());
    }
}
