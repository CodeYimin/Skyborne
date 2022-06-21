package events;

import components.Stat;

public class StatChangeEvent implements Event {
    private final Stat stat;
    private final int oldValue;
    private final int newValue;

    public StatChangeEvent(Stat stat, int oldValue, int newValue) {
        this.stat = stat;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public Stat getStat() {
        return stat;
    }

    public int getOldValue() {
        return oldValue;
    }

    public int getNewValue() {
        return newValue;
    }
}
