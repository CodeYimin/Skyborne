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
    public void update(double deltaTime) {
        Transform transform = getGameObject().getComponent(Transform.class);
        if (transform == null) {
            return;
        }

        transform.setPosition(transform.getPosition().add(getVelocity().multiply(deltaTime)));
    }
}
