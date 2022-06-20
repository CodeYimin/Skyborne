package events;

public class HealthEvent implements Event {
    private final int oldHealth;
    private final int newHealth;

    public HealthEvent(int oldHealth, int newHealth) {
        this.oldHealth = oldHealth;
        this.newHealth = newHealth;
    }

    public int getOldHealth() {
        return oldHealth;
    }

    public int getNewHealth() {
        return newHealth;
    }
}
