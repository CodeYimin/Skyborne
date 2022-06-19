package components;

import java.awt.event.KeyEvent;

import core.GameObject;
import input.Keyboard;
import structures.Vector;

public class Player extends Component {
    private int interactKey = KeyEvent.VK_F;
    private boolean interacting = false;
    private GameObject equippedItem = null;

    @Override
    public void update(double deltaTime) {
        Keyboard keyboard = getGameObject().getScene().getGame().getKeyboard();
        if (keyboard.isKeyDown(interactKey)) {
            interacting = true;
        } else {
            interacting = false;
        }
    }

    public void equip(GameObject gameObject) {
        if (equippedItem != null) {
            dequip(equippedItem);
        }
        equippedItem = gameObject;
        gameObject.setParent(getGameObject());
        gameObject.getTransform().setLocalPosition(Vector.ZERO);
    }

    public void dequip(GameObject gameObject) {
        equippedItem = null;
        gameObject.setParent(null);
        gameObject.getTransform().setPosition(getGameObject().getTransform().getPosition());
    }

    public boolean isInteracting() {
        return interacting;
    }
}
