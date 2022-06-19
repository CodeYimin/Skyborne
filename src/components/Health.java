package components;

public class Health extends Component {
    private int health;
    private int maxHealth;

    public Health(int maxHealth) {
        this.health = maxHealth;
        this.maxHealth = maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void damage(int damage) {
        this.health -= damage;
    }

    public void heal(int heal) {
        if (this.health < maxHealth) {
            this.health += heal;
        }
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
}
