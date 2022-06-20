package components;

import events.EventManager;
import events.HealthEvent;

public class Health extends Component {
    private int health;
    private int maxHealth;
    private EventManager<HealthEvent> eventManager = new EventManager<>();

    public Health(int maxHealth) {
        this.health = maxHealth;
        this.maxHealth = maxHealth;
    }

    public EventManager<HealthEvent> getEventManager() {
        return eventManager;
    }

    public int getCurrent() {
        return health;
    }

    public void setCurrent(int newHealth) {
        int oldHealth = this.health;
        this.health = newHealth;
        eventManager.emit(new HealthEvent(oldHealth, newHealth));
    }

    public void damage(int damage) {
        setCurrent(health - damage);
    }

    public void heal(int heal) {
        if (this.health < maxHealth) {
            setCurrent(health + heal);
        }
    }

    public int getMax() {
        return maxHealth;
    }

    public void setMax(int maxHealth) {
        this.maxHealth = maxHealth;
    }
}
