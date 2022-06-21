package events;

import java.util.ArrayList;

public class EventManager {
    private ArrayList<EventListener<?>> listeners = new ArrayList<>();

    public void addListener(EventListener<?> listener) {
        listeners.add(listener);
    }

    public void removeListener(EventListener<?> listener) {
        listeners.remove(listener);
    }

    public <T extends Event> void emit(T event) {
        listeners.sort(new EventListenerPriorityComparator());
        for (EventListener<?> listener : listeners) {
            if (listener.getEventClass() == event.getClass()) {
                // Guaranteed cast because event.getClass() is Class<T> so
                // listener.getEventClass() is Class<T>, so listener is EventListener<T>
                @SuppressWarnings("unchecked")
                EventListener<T> typedListener = (EventListener<T>) listener;
                typedListener.onEvent(event);
            }
        }
    }
}
