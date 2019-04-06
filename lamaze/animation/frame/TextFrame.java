package lamaze.animation.frame;

import java.util.function.Consumer;

public class TextFrame extends Frame {

    private String text;

    @Deprecated
    public TextFrame(String text, long delay) {
        super(delay);
        this.text = text;
    }
    public TextFrame(String text, long delay, Consumer function) {
        super(delay, function);
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
