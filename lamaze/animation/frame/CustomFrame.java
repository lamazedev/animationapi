package lamaze.animation.frame;

import java.util.function.Consumer;

public class CustomFrame extends Frame {

    private Object argument = null;

    public CustomFrame(Consumer consumer, Object argument, long delay) {
        super(delay, consumer);
        this.argument = argument;
    }
    public CustomFrame(Consumer consumer, Object argument) {
        super(consumer);
        this.argument = argument;
    }

    public Object getArgument() {
        return argument;
    }
}
