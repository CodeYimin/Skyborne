package events;

public class StatChangeEvent implements Event {
    private final int oldValue;
    private final int newValue;

    public StatChangeEvent(int oldValue, int newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public int getOldValue() {
        return oldValue;
    }

    public int getNewValue() {
        return newValue;
    }
}
