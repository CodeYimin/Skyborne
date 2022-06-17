package entities;

import input.InputManager;
import input.PlayerControls;
import util.Vector;

public class PlayerMovementController implements MovementController {
    private InputManager inputManager;
    private PlayerControls playerControls;

    public PlayerMovementController(InputManager inputManager, PlayerControls playerControls) {
        this.inputManager = inputManager;
        this.playerControls = playerControls;
    }

    @Override
    public Vector getMovementInput() {
        Vector movementInput = Vector.ZERO;

        if (inputManager.isKeyDown(playerControls.getMoveUp())) {
            movementInput = movementInput.add(Vector.UP);
        }
        if (inputManager.isKeyDown(playerControls.getMoveDown())) {
            movementInput = movementInput.add(Vector.DOWN);
        }
        if (inputManager.isKeyDown(playerControls.getMoveLeft())) {
            movementInput = movementInput.add(Vector.LEFT);
        }
        if (inputManager.isKeyDown(playerControls.getMoveRight())) {
            movementInput = movementInput.add(Vector.RIGHT);
        }

        return movementInput;
    }
}
