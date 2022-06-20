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

    public int getHealth() {
        return health;
    }

    public void setHealth(int newHealth) {
        int oldHealth = this.health;
        this.health = newHealth;
        eventManager.emit(new HealthEvent(oldHealth, newHealth));
    }

    public void damage(int damage) {
        setHealth(health - damage);
    }

    public void heal(int heal) {
        if (this.health < maxHealth) {
            setHealth(health + heal);
        }
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
}
