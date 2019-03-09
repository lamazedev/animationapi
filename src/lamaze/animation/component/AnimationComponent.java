package lamaze.animation.component;

import lamaze.animation.IAnimation;

import javax.swing.*;

public class    AnimationComponent<C extends JComponent> extends IAnimation {

    public AnimationComponent(C component) {
        super(component);
    }
    public AnimationComponent() {

    }
}
