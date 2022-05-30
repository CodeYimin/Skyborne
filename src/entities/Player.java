package entities;

import core.UpdateInfo;
import graphics.Sprite;
import input.VectorCompositeBinding;
import util.Vector;

public class Player extends Entity {
    private double speed = 5;
    private VectorCompositeBinding movementControls;

    public Player(VectorCompositeBinding movementControls) {
        this.movementControls = movementControls;

        setSprite(new Sprite("../assets/player.jpg"));
        setAcceleration(new Vector(0, -5));
    }

    @Override
    public void update(UpdateInfo updateInfo) {
        super.update(updateInfo);

        Vector movementInput = movementControls.getValue();
        Vector direction = movementInput.normalize();
        Vector newVelocity = direction.multiply(speed);

        if (getVelocity().getY() != 0) {
            newVelocity = new Vector(newVelocity.getX(), getVelocity().getY());
        }

        setVelocity(newVelocity);
    }
}
