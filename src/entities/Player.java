package entities;

import core.UpdateInfo;
import graphics.Sprite;
import input.InputManager;
import input.PlayerControls;
import scenes.Level;
import util.Vector;

public class Player extends Entity {
    private double speed = 5;
    private InputManager inputManager;
    private PlayerControls playerControls;

    public Player(Level level, InputManager inputManager) {
        super(level);
        this.inputManager = inputManager;

        setSprite(new Sprite("../assets/player.jpg"));
        // setAcceleration(new Vector(0, -50));
    }

    @Override
    public void update(UpdateInfo updateInfo) {
        super.update(updateInfo);

        Vector newVelocity = Vector.ZERO;

        if (inputManager.isKeyDown(playerControls.left)) {
            newVelocity = newVelocity.add(Vector.LEFT.multiply(speed));
        }
        if (inputManager.isKeyDown(playerControls.right)) {
            newVelocity = newVelocity.add(Vector.RIGHT.multiply(speed));
        }

        if (inputManager.isKeyDown(playerControls.up)) {
            newVelocity = newVelocity.add(Vector.UP.multiply(speed));
        }
        if (inputManager.isKeyDown(playerControls.down)) {
            newVelocity = newVelocity.add(Vector.DOWN.multiply(speed));
        }

        setVelocity(newVelocity);
    }

    public PlayerControls getControls() {
        return playerControls;
    }

    public void setControls(PlayerControls playerControls) {
        this.playerControls = playerControls;
    }
}
