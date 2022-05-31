package entities;

import core.UpdateInfo;
import graphics.Sprite;
import input.InputManager;
import input.PlayerControls;
import scenes.Level;
import util.Size;
import util.Vector;

public class Player extends Entity {
    private double speed = 5;
    private InputManager inputManager;
    private PlayerControls playerControls;

    public Player(Level level, InputManager inputManager) {
        super(level);
        this.inputManager = inputManager;

        setSprite(new Sprite("../assets/player.jpg"));
        setAcceleration(new Vector(0, -15));
        setSize(new Size(5, 2));
    }

    @Override
    public void update(UpdateInfo updateInfo) {
        super.update(updateInfo);

        Vector newVelocity = getVelocity();

        // X movement input
        if (inputManager.isKeyDown(playerControls.left)) {
            newVelocity = newVelocity.withX(-speed);
        }
        if (inputManager.isKeyDown(playerControls.right)) {
            newVelocity = newVelocity.withX(speed);
        }
        if (!inputManager.isKeyDown(playerControls.right) && !inputManager.isKeyDown(playerControls.left)) {
            newVelocity = newVelocity.withX(0);
        }

        // Y movement input
        if (isGrounded() && inputManager.isKeyDown(playerControls.up)) {
            newVelocity = newVelocity.withY(speed * 1.2);
        }
        if (inputManager.isKeyDown(playerControls.down)) {
            newVelocity = newVelocity.withY(-speed);
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
