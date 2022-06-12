package entities;

import graphics.Sprite;
import input.InputManager;
import input.PlayerControls;
import scenes.World;
import util.Side;
import util.Size;
import util.Vector;

public class Player extends Entity {
    private double speed = 5;
    private InputManager inputManager;
    private PlayerControls playerControls;
    private Weapon weapon;

    public Player(World level, InputManager inputManager) {
        super(level);
        this.inputManager = inputManager;

        setSprite(new Sprite("../assets/player.jpg"));
        setAcceleration(Vector.DOWN.multiply(15));
        setSize(new Size(1, 1));
        setPhasesTiles(false);

        weapon = new Weapon(this, Vector.UP.multiply(0.2));
    }

    @Override
    public void update() {
        super.update();

        keyboardMovement();
        keyboardShoot();
    }

    public void keyboardMovement() {
        Vector newVelocity = Vector.ZERO;

        // X movement
        if (inputManager.isKeyDown(playerControls.left)) {
            newVelocity = newVelocity.add(-speed);
            setDirection(Vector.LEFT_DIRECTION);
        }
        if (inputManager.isKeyDown(playerControls.right)) {
            newVelocity = newVelocity.add(speed);
            setDirection(Vector.RIGHT_DIRECTION);
        }

        // Y movement
        if (getTileCollidingSides().contains(Side.BOTTOM) && inputManager.isKeyDown(playerControls.up)) {
            newVelocity = newVelocity.withY(speed * 1.2);
        } else {
            newVelocity = newVelocity.withY(getVelocity().y());
        }

        setVelocity(newVelocity);
    }

    public void keyboardShoot() {
        if (inputManager.isKeyDown(playerControls.attack)) {
            weapon.shoot();
        }
    }

    public PlayerControls getControls() {
        return playerControls;
    }

    public void setControls(PlayerControls playerControls) {
        this.playerControls = playerControls;
    }
}
