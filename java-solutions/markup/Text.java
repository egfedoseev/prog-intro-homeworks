package markup;

public class Text extends Element {
    private final String st;

    public Text(String st) {
        this.st = st;
    }

    public Text() {
        this.st = "";
    }

    @Override
    public void toMarkdown(StringBuilder s) {
        s.append(st);
    }

    @Override
    public void toBBCode(StringBuilder s) {
        s.append(st);
    }

    @Override
    public void toHTML(StringBuilder s) {
        for (int i = 0; i < st.length(); ++i) {
            if (st.charAt(i) == '<') {
                s.append("&lt;");
            } else if (st.charAt(i) == '>') {
                s.append("&gt;");
            } else if (st.charAt(i) == '&') {
                s.append("&amp;");
            } else if (st.charAt(i) == '\\' && i + 1 < st.length()) {
                continue;
            } else {
                s.append(st.charAt(i));
            }
        }
    }
}
