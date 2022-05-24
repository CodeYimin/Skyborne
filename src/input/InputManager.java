package input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;

public class InputManager extends KeyAdapter {
    private final HashSet<Integer> keysHeld = new HashSet<>();

    @Override
    public void keyPressed(KeyEvent event) {
        keysHeld.add(event.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent event) {
        keysHeld.remove(event.getKeyCode());
    }

    public HashSet<Integer> getKeysHeld() {
        return (HashSet<Integer>) keysHeld.clone();
    }
}