package lamaze.animation.frame;

import java.awt.*;
import java.util.function.Consumer;

public class ColorFrame extends Frame {

    private Color color;

    @Deprecated
    public ColorFrame(Color color) {
        this.color = color;
    }

    @Deprecated
    public ColorFrame(Color color, long delay) {
        super(delay);
        this.color = color;
    }

    public ColorFrame(Color color, long delay, Consumer function) {
        super(delay, function);
        this.color = color;
    }
    public ColorFrame(Color color, Consumer function) {
        super(function);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
