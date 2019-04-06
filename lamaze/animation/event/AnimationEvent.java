package lamaze.animation.event;

public class AnimationEvent {

    private Type type;

    public AnimationEvent(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        FINISHED_All_FRAMES
    }

}
