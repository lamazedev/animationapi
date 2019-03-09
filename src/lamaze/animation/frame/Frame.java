package lamaze.animation.frame;

import java.util.function.Consumer;

public abstract class Frame {

    private long delay = 0;
    private Consumer function = null;

    public Frame(long delay) {
        this.delay = delay;
    }
    public Frame(long delay, Consumer function) {
        this.delay = delay;
        this.function = function;
    }
    public Frame(Consumer function) {
        this.function = function;
    }
    public Frame() {

    }

    public long getDelay() {
        return delay;
    }
    public void accept(Object accept) {
        function.accept(accept);
    }
    public boolean hasConsumer() {
        return function != null;
    }
}
