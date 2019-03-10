# Swing Animation Library

This is to help Java swing developers create quick and easy animations within their swing application.

## How to use

One of the best ways to learn is by giving examples. Here below is an example of how we can create something like this:
![Example](https://i.gyazo.com/e78b14656e71281c0295c8d640777ced.gif)
```java
public class MainFrame extends JFrame {

    public MainFrame() {
        //Instantiate the JComponent we want to animate
        final JLabel label = new JLabel("Test");

        //Create a Animation.Builder object
        final Animation.Builder animationBuilder = new Animation.Builder<>()
                .setComponent(label) //Add the JComponent we want to animate
                .willStartImmediately(true) //Start the animation right away (optional)
                .withListener(event -> { //Add event listeners (optional)
                    AnimationEvent.Type type = event.getType();
                    if(type == AnimationEvent.Type.FINISHED_All_FRAMES) {
                        System.out.println("Completed a full cycle of frames...");
                        //Optional handling of frame events
                    }
                });

        final StringBuilder appendage = new StringBuilder();
        for(char character : "animation".toCharArray()) {
            appendage.append(character);
            final String text = appendage.toString();
			
			//the parameters for this TextFrame object are go like: text, delay
            animationBuilder.withFrame(new TextFrame(String.format("Wow! This is the %s API", text), text.length() == 9 ? 1500 : 125));
        }
        //Finally <i>build</i> the Animation if we haven't included Animation.Builder#willStartImmediately we can
        //use AnimationComponent#startAnimation(index) or AnimationComponent#startAllAnimations()
        final AnimationComponent animationComponent = animationBuilder.build();

        add(label);

        setSize(new Dimension(250, 85));
        setLayout(new FlowLayout());
        setTitle("Animation Test Frame");
        setVisible(true);
    }

}
```

## Concerns

Although using the deprecated Frame constructors are quick and easy, there isn't always a JComponent#setText method you can rely on the library to use. It is recommended you use a Consumer for frames as it is much more efficient and isn't as time <i>consum</i>ing as you think. Here is an example to using consumers with this library in respect to the example code above:
```java
final StringBuilder appendage = new StringBuilder();
Consumer<String> textConsumer = label::setText; //Consumer for the JLabel#setText method
for(char character : "animation".toCharArray()) {
    appendage.append(character);
    final String text = appendage.toString();

    animationBuilder.withFrame(
        new TextFrame(
            String.format("Wow! This is the %s API", text), //Text for this animation
            text.length() == 9 ? 1500 : 125, //delay (aka if text is equal to 9 set the delay to 1500 else 125)
            textConsumer //The consumer we assigned outside the for loop
        )
    );
}
```

## Your own animations
![Example](https://i.gyazo.com/6e8708b0092a1c2367d7ae1709c0514a.gif)
```java
final StringBuilder appendage = new StringBuilder();

for(char character : "animation".toCharArray()) {
    appendage.append(character);
    final String text = appendage.toString();

    animationBuilder.withFrames(
        new FontFrame(new Font("Arial", Font.PLAIN, text.length() + 6), (Consumer<Font>) label::setFont),
        new ColorFrame(text.length() == 9 ? Color.DARK_GRAY : Color.RED, (Consumer<Color>) label::setForeground),
        //You can also add a frame of your own. We make sure to put these frames before the TextFrame because it freezes the animation thread for its delay
        //Background frame:
        new CustomFrame((Consumer<Color>) label::setBackground, text.length() == 9 ? Color.BLACK : Color.WHITE),
        new TextFrame(String.format("Wow! This is the %s API", text), text.length() == 9 ? 1500 : 125, (Consumer<String>) label::setText)
    );
}
```
