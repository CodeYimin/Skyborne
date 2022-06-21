package components;

import events.StatChangeEvent;

public abstract class Stat extends Component {
    private int current;
    private int max;

    public Stat(int max) {
        this.current = max;
        this.max = max;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int newValue) {
        int oldValue = this.current;
        this.current = newValue;
        getGameObject().emitEvent(new StatChangeEvent(this, oldValue, newValue));
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
