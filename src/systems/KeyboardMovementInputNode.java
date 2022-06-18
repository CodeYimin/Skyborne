package systems;

import components.Vector;
import input.MovementControls;

public class KeyboardMovementInputNode implements SystemNode {
    private MovementControls movementControls;
    private Vector velocity;
    private double speed;

    public KeyboardMovementInputNode(MovementControls movementControls, Vector velocity, double speed) {
        this.movementControls = movementControls;
        this.velocity = velocity;
        this.speed = speed;
    }

    public MovementControls getMovementControls() {
        return movementControls;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public double getSpeed() {
        return speed;
    }
}
