package systems;

import components.Vector;
import input.MovementControls;

public class KeyboardMovementInputSystem extends System<KeyboardMovementInputNode> {
    public KeyboardMovementInputSystem(int priority) {
        super(priority);
    }

    @Override
    public void updateNode(KeyboardMovementInputNode node, double deltaTime) {
        Vector velocity = node.getVelocity();
        double speed = node.getSpeed();
        MovementControls movementControls = node.getMovementControls();

        velocity.set(0, 0);

        if (movementControls.isMoveUp()) {
            velocity.addY(speed);
        }
        if (movementControls.isMoveDown()) {
            velocity.addY(-speed);
        }
        if (movementControls.isMoveLeft()) {
            velocity.addX(-speed);
        }
        if (movementControls.isMoveRight()) {
            velocity.addX(speed);
        }
    }
}
