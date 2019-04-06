package lamaze.animation.main;

import lamaze.animation.Animation;
import lamaze.animation.frame.ColorFrame;
import lamaze.animation.frame.CustomFrame;
import lamaze.animation.frame.FontFrame;
import lamaze.animation.frame.TextFrame;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class MainFrame extends JFrame {

    public MainFrame() {
        //Instantiate the JComponent we want to animate
        final JLabel label = new JLabel("Test");
        label.setOpaque(true);

        //Create a Animation.Builder object
        final Animation.Builder animationBuilder = new Animation.Builder<>()
                .setComponent(label)
                .willStartImmediately(true);

        final StringBuilder appendage = new StringBuilder();

        for(char character : "animation".toCharArray()) {
            appendage.append(character);
            final String text = appendage.toString();

            animationBuilder.withFrames(
                    new FontFrame(new Font("Arial", Font.PLAIN, text.length() + 6), (Consumer<Font>) label::setFont),
                    new ColorFrame(text.length() == 9 ? Color.DARK_GRAY : Color.RED, (Consumer<Color>) label::setForeground),
                    //You can also add a frame of your own. We make sure to put these frames before the TextFrame because it freezes the animation thread for its delay
                    //Background frame:
                    new CustomFrame((Consumer<Color>) label::setBackground, text.length() == 9 ? Color.WHITE : Color.BLACK),
                    new TextFrame(String.format("Wow! This is the %s API", text), text.length() == 9 ? 1500 : 125, (Consumer<String>) label::setText)
            );
        }

        animationBuilder.build();

        add(label);

        setSize(new Dimension(250, 85));
        setLayout(new FlowLayout());
        setTitle("Animation Test Frame");
        setVisible(true);
    }

}
