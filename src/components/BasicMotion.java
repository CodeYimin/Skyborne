package components;

import structures.Vector;

public class BasicMotion extends Motion {
    public BasicMotion() {
        super();
    }

    public BasicMotion(Vector velocity) {
        super(velocity);
    }

    @Override
    public void move(double deltaTime) {
        Transform transform = getGameObject().getTransform();
        if (transform == null) {
            return;
        }

        transform.setPosition(transform.getPosition().add(getVelocity().multiply(deltaTime)));
    }
}
