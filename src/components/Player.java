package components;

import java.awt.event.KeyEvent;

import core.GameObject;
import input.Keyboard;
import input.Mouse;
import structures.Vector;

public class Player extends Component {
    private int interactKey = KeyEvent.VK_F;
    private boolean interacting = false;
    private Weapon weapon = null;

    @Override
    public void update(double deltaTime) {
        checkKeyboardInteraction();
        mouseFire();
        rotateWeaponToMouse();
    }

    private void checkKeyboardInteraction() {
        Keyboard keyboard = getGameObject().getScene().getGame().getKeyboard();

        // Interaction
        if (keyboard.isKeyDown(interactKey)) {
            interacting = true;
        } else {
            interacting = false;
        }
    }

    private void mouseFire() {
        if (weapon == null) {
            return;
        }

        Mouse mouse = getGameObject().getScene().getGame().getMouse();
        if (mouse.isPressed()) {
            weapon.fire();
        }
    }

    private void rotateWeaponToMouse() {
        Camera camera = getGameObject().getScene().getComponent(Camera.class);
        if (weapon == null || camera == null) {
            return;
        }

        Mouse mouse = getGameObject().getScene().getGame().getMouse();
        Vector mouseScreenPosition = mouse.getPosition();
        if (mouseScreenPosition != null) {
            Vector mousePosition = camera.screenToWorldPosition(mouseScreenPosition);
            Vector direction = mousePosition.subtract(getGameObject().getTransform().getPosition());
            weapon.getGameObject().getTransform().setRotation(direction.toAngle());
        }
    }

    public boolean equipWeapon(GameObject gameObject) {
        Weapon weapon = gameObject.getComponent(Weapon.class);
        if (weapon == null) {
            return false;
        } else {
            return equipWeapon(weapon);
        }
    }

    public boolean equipWeapon(Weapon weapon) {
        if (weapon.getGameObject() == null) {
            return false;
        }

        if (this.weapon != null) {
            dequip();
        }

        this.weapon = weapon;
        weapon.getGameObject().setParent(getGameObject());
        return true;
    }

    public void dequip() {
        weapon.getGameObject().setParent(null);
        weapon.getGameObject().getTransform().setPosition(getGameObject().getTransform().getPosition());
        weapon = null;
    }

    public boolean isInteracting() {
        return interacting;
    }
}
