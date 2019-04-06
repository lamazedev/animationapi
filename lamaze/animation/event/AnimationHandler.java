package lamaze.animation.event;

import java.util.ArrayList;
import java.util.List;

public class AnimationHandler {

    private List<AnimationListener> listeners = new ArrayList<>();

    public void addListener(AnimationListener listener) {
        listeners.add(listener);
    }

    public void animationEvent(AnimationEvent event) {
        for (AnimationListener listener : listeners) {
            listener.onAnimationEvent(event);
        }
    }

}
