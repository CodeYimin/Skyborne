package events;

import java.util.ArrayList;

public class EventManager<T extends Event> {
    private ArrayList<EventListener<T>> listeners = new ArrayList<EventListener<T>>();

    public void addListener(EventListener<T> listener) {
        listeners.add(listener);
    }

    public void removeListener(EventListener<T> listener) {
        listeners.remove(listener);
    }

    public void emit(T event) {
        listeners.sort(new EventListenerPriorityComparator());
        for (EventListener<T> listener : listeners) {
            listener.onEvent(event);
        }
    }
}
