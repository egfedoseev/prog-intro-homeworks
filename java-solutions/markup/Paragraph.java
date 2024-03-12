package markup;

import java.util.List;

public class Paragraph extends ParagraphAndMarkableList implements Markdown, HTML {
    private final List<Element> list;

    public Paragraph(List<Element> list) {
        this.list = List.copyOf(list);
    }

    protected String tagHTMLOpen() {
        return "<p>";
    }

    protected String tagHTMLClose() {
        return "</p>";
    }

    @Override
    public void toMarkdown(StringBuilder s) {
        for (Element item : list) {
            item.toMarkdown(s);
        }
    }

    @Override
    public void toHTML(StringBuilder s) {
        s.append(tagHTMLOpen());
        for (Element item : list) {
            item.toHTML(s);
        }
        s.append(tagHTMLClose());
        s.append(System.lineSeparator());
    }

    @Override
    public void toBBCode(StringBuilder s) {
        for (Element item : list) {
            item.toBBCode(s);
        }
    }
}
