package entities;

import core.UpdateInfo;
import graphics.renderers.Renderer;
import graphics.renderers.SpriteRenderer;
import input.VectorCompositeBinding;
import util.Size;
import util.Vector;

public class Player extends GameObject {
    private double speed = 5;
    private Vector velocity = new Vector(0, 0);
    private Vector acceleration = new Vector(0, -1);
    private VectorCompositeBinding movementControls;

    public Player(VectorCompositeBinding movementControls) {
        this.movementControls = movementControls;

        // Renderer renderer = new RectangleRenderer(new Size(1, 1));
        Renderer renderer = new SpriteRenderer("../assets/player.jpg", new Size(1, 1));
        setRenderer(renderer);
    }

    @Override
    public void update(UpdateInfo updateInfo) {
        Vector movementInput = movementControls.getValue();
        Vector direction = movementInput.normalize();
        velocity = direction.multiply(speed).multiply(updateInfo.deltaTimeSeconds);

        move();
    }

    private void move() {
        setPosition(getPosition().add(velocity));
    }
}
