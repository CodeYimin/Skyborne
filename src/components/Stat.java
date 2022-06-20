package components;

import events.EventListener;
import events.EventManager;
import events.StatChangeEvent;

public abstract class Stat extends Component {
    private int current;
    private int max;
    private EventManager<StatChangeEvent> eventManager = new EventManager<>();

    public Stat(int max) {
        this.current = max;
        this.max = max;
    }

    public void addChangeListener(EventListener<StatChangeEvent> listener) {
        eventManager.addListener(listener);
    }

    public void removeStatChangeListener(EventListener<StatChangeEvent> listener) {
        eventManager.removeListener(listener);
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int newValue) {
        int oldValue = this.current;
        this.current = newValue;
        eventManager.emit(new StatChangeEvent(oldValue, newValue));
    }

    public void decrease(int amount) {
        setCurrent(current - amount);
    }

    public void increase(int amount) {
        if (this.current < max) {
            setCurrent(current + amount);
        }
    }

    public int getMax() {
        return max;
    }

    public void setMax(int maxHealth) {
        this.max = maxHealth;
    }
}
