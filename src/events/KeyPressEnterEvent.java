package events;

public class KeyPressEnterEvent implements Event {
    private final int key;

    public KeyPressEnterEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
