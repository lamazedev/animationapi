package lamaze.animation;

import lamaze.animation.component.AnimationComponent;
import lamaze.animation.event.AnimationEvent;
import lamaze.animation.event.AnimationHandler;
import lamaze.animation.event.AnimationListener;
import lamaze.animation.frame.*;
import lamaze.animation.frame.Frame;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;

public class Animation<T extends IAnimation> {

    public static final int TEXT = 1;
    public static final int COLOR = 2;
    public static final int FONT = 3;
    public static final int SIZE = 4;

    private JComponent component;
    private List<Frame> frames = new ArrayList<>();
    private Thread thread = null;
    private boolean running = false;
    private AnimationHandler eventHandler;

    public Animation(T animationComponent, List<Frame> frames) {
        this.component = animationComponent.getComponent();
        this.frames = frames;
        this.eventHandler = new AnimationHandler();

        thread = new Thread(() -> {
            if(frames.size() == 0)
                throw new IllegalArgumentException("Frames in list provided are empty");
            if(thread == null)
                throw new NullPointerException("Thread is null");

            while(true) {
                while(running) {
                    Map<Frame, Method> methods = new HashMap<>();

                    for (Frame f : frames) {
                        Type frameType = getType(f);
                        if(frameType == null)
                            throw new IllegalArgumentException("Could not get frame type");

                        long delay = f.getDelay();
                        switch(frameType) {
                            case FONT:
                                FontFrame fontFrame = (FontFrame) f;
                                if(fontFrame.hasConsumer()) {
                                    fontFrame.accept(fontFrame.getFont());
                                } else {
                                    Method method = methods.get(component);
                                    String methodName = "setFont";
                                    if (method == null) {
                                        try {
                                            method = component.getClass().getMethod(methodName, Font.class);
                                        } catch (NoSuchMethodException e) {
                                            System.out.println("[Animation Library] - Could not find '" + methodName + "' method. Please specify a consumer! Ending animation...");
                                        }
                                        methods.put(fontFrame, method);
                                    }
                                    boolean success = isMethodWorking(component, method, fontFrame.getFont());
                                    if(success) {
                                        methods.put(fontFrame, method);
                                    } else {
                                        try {
                                            stop();
                                        } catch (InterruptedException e) {}
                                    }
                                }
                                break;





                            case TEXT:
                                TextFrame textFrame  = (TextFrame) f;
                                if(textFrame.hasConsumer()) {
                                    textFrame.accept(textFrame.getText());
                                } else {
                                    Method method = methods.get(component);
                                    String methodName = "setText";
                                    if (method == null) {
                                        try {
                                            method = component.getClass().getMethod(methodName, String.class);
                                        } catch (NoSuchMethodException e) {
                                            System.out.println("[Animation Library] - Could not find '" + methodName + "' method. Please specify a consumer! Ending animation...");
                                        }
                                        methods.put(textFrame, method);
                                    }
                                    boolean success = isMethodWorking(component, method, textFrame.getText());
                                    if(success) {
                                        methods.put(textFrame, method);
                                    } else {
                                        try {
                                            stop();
                                        } catch (InterruptedException e) {}
                                    }
                                }
                                break;
                            case COLOR:
                                ColorFrame colorFrame  = (ColorFrame) f;
                                if(colorFrame.hasConsumer()) {
                                    colorFrame.accept(colorFrame.getColor());
                                } else {
                                    Method method = methods.get(component);
                                    String methodName = "setForeground";
                                    if (method == null) {
                                        try {
                                            method = component.getClass().getMethod(methodName, Color.class);
                                        } catch (NoSuchMethodException e) {
                                            System.out.println("[Animation Library] - Could not find '" + methodName + "' method. Please specify a consumer! Ending animation...");
                                        }
                                        methods.put(colorFrame, method);
                                    }
                                    boolean success = isMethodWorking(component, method, colorFrame.getColor());
                                    if(success) {
                                        methods.put(colorFrame, method);
                                    } else {
                                        try {
                                            stop();
                                        } catch (InterruptedException e) {}
                                    }
                                }
                                break;
                            case CUSTOM:
                                CustomFrame customFrame = (CustomFrame) f;
                                customFrame.accept(customFrame.getArgument());
                                System.out.println(customFrame.hasConsumer() + " | " + customFrame.getDelay() + " | " + customFrame.getArgument());
                                break;
                        }
                        if(delay <= 0) continue;

                        try {
                            Thread.sleep(delay);
                        } catch (InterruptedException e) {
                            System.out.println("[Animation Library] - Failed to sleep animation thread");
                        }
                    }
                    eventHandler.animationEvent(new AnimationEvent(AnimationEvent.Type.FINISHED_All_FRAMES));
                }
                break;
            }
        });
    }

    public void render() {

    }
    public void stop() throws InterruptedException {
        running = false;
    }
    public void start() {
        running = true;
        thread.start();
    }
    public boolean isRunning() {
        return running;
    }

    public void addListener(AnimationListener listener) {
        eventHandler.addListener(listener);
    }

    protected JComponent getComponent() {
        return component;
    }

    protected List<Frame> getFrames() {
        return frames;
    }

    private Type getType(Frame frame) {
        if(frame instanceof TextFrame)
            return Type.TEXT;
        if(frame instanceof FontFrame)
            return Type.FONT;
        if(frame instanceof ColorFrame)
            return Type.COLOR;
        if(frame instanceof CustomFrame)
            return Type.CUSTOM;

        return null;
    }

    private boolean isMethodWorking(JComponent component, Method method, Object... arguments) {
        try {
            method.invoke(component, arguments);
            return true;
        } catch (IllegalAccessException e) {
            System.out.println("[Animation Library] - Caught IllegalAccessException. Retrying with 'setAccessible'. Please specify a consumer.");
            try {
                method.setAccessible(true);
                method.invoke(component, arguments);
                return true;
            } catch (IllegalAccessException ex) {
                System.out.println("[Animation Library] - Caught IllegalAccessException  with 'setAccessible(true)'. Please specify a consumer.");
                return false;
            } catch (InvocationTargetException ex) {
                System.out.println("[Animation Library] - When trying to invoke '" + method.getName() + "' with 'setAccessible(true)' method we caught an error. Please specify a consumer.");
                return false;
            }
        } catch (InvocationTargetException e) {
            System.out.println("[Animation Library] - When trying to invoke '" + method.getName() + "' method we caught an error. Please specify a consumer.");
            return false;
        }
    }

    private enum Type {
        TEXT,
        COLOR,
        FONT,
        CUSTOM
    }

    public static class Builder<T extends JComponent> {

        private List<Frame> frames = new ArrayList<>();
        private List<AnimationListener> listeners = new ArrayList<>();
        private AnimationComponent<T> animationComponent = null;
        private boolean startImmediately = false;

        //Set component that will be animated
        public Builder setComponent(T component) {
            this.animationComponent = new AnimationComponent<>(component);
            return this;
        }
        //Add provided frames to the current list of frames
        public Builder withFrames(List<Frame> frames) {
            this.frames.addAll(frames);
            return this;
        }
        //Set the current list with a java.util.List object
        public Builder setFrames(List<Frame> frames) {
            this.frames = frames;
            return this;
        }

        //Add frames via. a 1d array list
        public Builder withFrames(Frame... frames) {
            for (Frame frame : frames)
                this.frames.add(frame);
            return this;
        }
        //Set the current list with a 1d array list
        public Builder setFrames(Frame... frames) {
            this.frames = Arrays.asList(frames);
            return this;
        }
        //Add a singular frame to the current list
        public Builder withFrame(Frame frame) {
            frames.add(frame);
            return this;
        }
        //Start the animation when Animation.Builder#build() starts
        public Builder willStartImmediately(boolean startImmediately) {
            this.startImmediately = startImmediately;
            return this;
        }
        //Add listener to animation
        public Builder withListener(AnimationListener listener) {
            this.listeners.add(listener);
            return this;
        }
        public List<Frame> getFrames() {
            return frames;
        }
        public AnimationComponent build() {
            if(animationComponent == null)
                throw new NullPointerException("You forgot to specify the animation component");
            if(frames.isEmpty())
                throw new NullPointerException("You forgot to add some frames");

            Animation animation = new Animation(animationComponent, frames);
            animationComponent.addAnimation(animation);
            if(startImmediately)
                animation.start();
            if(!listeners.isEmpty()) {
                for (AnimationListener listener : listeners) {
                    animation.addListener(listener);
                }
            }

            return animationComponent;
        }

    }

}
