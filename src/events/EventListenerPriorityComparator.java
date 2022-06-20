package events;

import java.util.Comparator;

public class EventListenerPriorityComparator implements Comparator<EventListener<?>> {
    public int compare(EventListener<?> a, EventListener<?> b) {
        // Make highest priority appear first on sorted list
        return b.getPriority() - a.getPriority();
    }
}