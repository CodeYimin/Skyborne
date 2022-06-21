package input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;

import events.EventListener;
import events.EventManager;
import events.KeyPressEnterEvent;

public class Keyboard extends KeyAdapter {
    private HashSet<Integer> keysDown = new HashSet<>();
    private EventManager eventManager = new EventManager();

    @Override
    public void keyPressed(KeyEvent event) {
        if (!keysDown.contains(event.getKeyCode())) {
            keysDown.add(event.getKeyCode());
            eventManager.emit(new KeyPressEnterEvent(event.getKeyCode()));
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        keysDown.remove(event.getKeyCode());
    }

    public boolean isKeyDown(int keyCode) {
        return keysDown.contains(keyCode);
    }

    public HashSet<Integer> getKeysHeld() {
        return keysDown;
    }

    public void addKeyPressEnterListener(EventListener<KeyPressEnterEvent> listener) {
        eventManager.addListener(listener);
    }
}