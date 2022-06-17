package entities;

import input.Keyboard;
import input.PlayerControls;
import util.Vector;

public class PlayerMovementController implements MovementController {
    private Keyboard keyboard;
    private PlayerControls playerControls;

    public PlayerMovementController(Keyboard inputManager, PlayerControls playerControls) {
        this.keyboard = inputManager;
        this.playerControls = playerControls;
    }

    @Override
    public Vector getMovementInput() {
        Vector movementInput = Vector.ZERO;

        if (keyboard.isKeyDown(playerControls.getMoveUp())) {
            movementInput = movementInput.add(Vector.UP);
        }
        if (keyboard.isKeyDown(playerControls.getMoveDown())) {
            movementInput = movementInput.add(Vector.DOWN);
        }
        if (keyboard.isKeyDown(playerControls.getMoveLeft())) {
            movementInput = movementInput.add(Vector.LEFT);
        }
        if (keyboard.isKeyDown(playerControls.getMoveRight())) {
            movementInput = movementInput.add(Vector.RIGHT);
        }

        return movementInput;
    }
}
