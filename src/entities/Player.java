package entities;

import graphics.Sprite;
import input.InputManager;
import input.PlayerControls;
import scenes.World;
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
        setGravity(15);
        setSize(new Size(1, 1));
        setAntiTileCollision(true);

        weapon = new Weapon(level);
    }

    @Override
    public void update() {
        super.update();

        Vector newVelocity = getVelocity();

        // X movement
        if (inputManager.isKeyDown(playerControls.left)) {
            newVelocity = newVelocity.withX(-speed);
        }
        if (inputManager.isKeyDown(playerControls.right)) {
            newVelocity = newVelocity.withX(speed);
        }
        if (!inputManager.isKeyDown(playerControls.right) && !inputManager.isKeyDown(playerControls.left)) {
            newVelocity = newVelocity.withX(0);
        }

        // Y movement
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
