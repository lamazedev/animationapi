package lamaze.animation.frame;

import java.awt.*;
import java.util.function.Consumer;

public class FontFrame extends lamaze.animation.frame.Frame {

    private Font font;
    private String name;
    private int style = 0;
    private int size = 12;

    @Deprecated
    public FontFrame(Font font) {
        this.font = font;
        this.style = font.getStyle();
        this.size = font.getSize();
        this.name = font.getName();
    }

    @Deprecated
    public FontFrame(Font font, long delay) {
        super(delay);
        this.font = font;
        this.style = font.getStyle();
        this.size = font.getSize();
        this.name = font.getName();
    }
    public FontFrame(Font font, long delay, Consumer consumer) {
        super(delay, consumer);
        this.font = font;
        this.style = font.getStyle();
        this.size = font.getSize();
        this.name = font.getName();
    }
    public FontFrame(Font font, Consumer consumer) {
        super(consumer);
        this.font = font;
        this.style = font.getStyle();
        this.size = font.getSize();
        this.name = font.getName();
    }

    public Font getFont() {
        return font;
    }

    public int getStyle() {
        return style;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }
}
