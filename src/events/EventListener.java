package events;

public abstract class EventListener<T extends Event> {
    private Class<T> eventClass;
    private int priority;

    public EventListener(Class<T> eventClass, int priority) {
        this.eventClass = eventClass;
        this.priority = priority;
    }

    public Class<T> getEventClass() {
        return eventClass;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public abstract void onEvent(T event);
}
