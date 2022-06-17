package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;

public class Keyboard implements KeyListener {
    private HashSet<Integer> keysDown = new HashSet<>();
    private ArrayList<KeyListener> keyListeners = new ArrayList<>();

    @Override
    public void keyPressed(KeyEvent event) {
        keysDown.add(event.getKeyCode());

        for (KeyListener listener : keyListeners) {
            listener.keyPressed(event);
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        keysDown.remove(event.getKeyCode());

        for (KeyListener listener : keyListeners) {
            listener.keyReleased(event);
        }
    }

    @Override
    public void keyTyped(KeyEvent event) {
        for (KeyListener listener : keyListeners) {
            listener.keyTyped(event);
        }
    }

    public boolean isKeyDown(int keyCode) {
        return keysDown.contains(keyCode);
    }

    public HashSet<Integer> getKeysHeld() {
        return keysDown;
    }

    public void addKeyListener(KeyListener listener) {
        keyListeners.add(listener);
    }

    public void removeKeyListener(KeyListener listener) {
        keyListeners.remove(listener);
    }
}