package events;

public abstract class EventListener<T extends Event> {
    private int priority;

    public EventListener(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public abstract void onEvent(T event);
}
