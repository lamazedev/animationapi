package lamaze.animation.util;

public class EasyPair<S, U> implements Pair {

    private S first;
    private U second;

    public EasyPair() {

    }
    public EasyPair(S first, U second) {
        this.first = first;
        this.second = second;
    }

    public void setFirst(S first) {
        this.first = first;
    }
    public void setSecond(U second) {
        this.second = second;
    }

    @Override
    public S getFirstValue() {
        return first;
    }

    @Override
    public U getSecondValue() {
        return second;
    }
}
