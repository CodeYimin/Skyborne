package components;

import java.util.ArrayList;

public class Health extends Component {
    private int health;
    private int maxHealth;
    private ArrayList<Listener> listeners = new ArrayList<>();

    public Health(int maxHealth) {
        this.health = maxHealth;
        this.maxHealth = maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        int oldHealth = this.health;
        this.health = health;
        for (Listener listener : listeners) {
            listener.onHealthChanged(this, oldHealth, health);
        }
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

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    public static interface Listener {
        public void onHealthChanged(Health health, int oldHealth, int newHealth);
    }
}
