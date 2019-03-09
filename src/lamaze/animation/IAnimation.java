package lamaze.animation;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public abstract class IAnimation<C extends JComponent> {

    private C component;
    private List<Animation> animations = new ArrayList<>();

    public IAnimation(C component) {
        this.component = component;
    }
    public IAnimation() {

    }


    public void addAnimation(Animation animation) {
        animations.add(animation);
    }

    public void stopAllAnimations() throws InterruptedException {
        for (Animation animation : animations) {
            if(!animation.isRunning())
                continue;

            animation.stop();
        }
    }
    public void stopAnimation(int index) throws InterruptedException {
        Animation animation = animations.get(index);
        if(animation == null)
            throw new ArrayIndexOutOfBoundsException("Index " + index + " is null");

        if(!animation.isRunning())
            return;

        animation.stop();
    }
    public void startAllAnimations() {
        for (Animation animation : animations) {
            if(animation.isRunning())
                continue;

            animation.start();
        }
    }
    public void startAnimation(int index) {
        Animation animation = animations.get(index);
        if(animation == null)
            throw new ArrayIndexOutOfBoundsException("Index " + index + " is null");

        if(animation.isRunning())
            return;

        animation.start();
    }
    public void setComponent(C component) {
        this.component = component;
    }

    public C getComponent() {
        return component;
    }

    public List<Animation> getAnimations() {
        return animations;
    }
}
