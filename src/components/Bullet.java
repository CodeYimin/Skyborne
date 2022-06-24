package components;

import core.GameObject;

public class Bullet extends Component {
    // The bullet will only destroy itself and damage game objects that contain this
    // component
    private Class<? extends Component> targetClass;
    private int damage;

    public Bullet(Class<? extends Component> targetClass, int damage) {
        this.targetClass = targetClass;
        this.damage = damage;
    }

    @Override
    public void update(double deltaTime) {
        BoxCollider boxCollider = getGameObject().getComponent(BoxCollider.class);
        if (boxCollider == null) {
            return;
        }

        // Destroy bullet and damage first colliding object
        boolean attacked = false;
        for (GameObject collidingObject : boxCollider.getCollidingObjects()) {
            if (collidingObject.getComponent(targetClass) != null && !attacked) {
                collidingObject.getComponent(Health.class).decrease(damage);
                getGameObject().destroy();
                attacked = true;
            }
        }

        // Destroy bullet on tile collision.
        // Check if bullet is destroyed first because it might've already collided with
        // a gameobject and destroyed itself
        if (!isDestroyed() && boxCollider.isCollidingTiles()) {
            getGameObject().destroy();
        }
    }
}
