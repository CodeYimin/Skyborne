package components;

import core.GameObject;

public class Bullet extends Component {
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

        boolean attacked = false;
        for (GameObject collidingObject : boxCollider.getCollidingObjects()) {
            if (collidingObject.getComponent(targetClass) != null && !attacked) {
                collidingObject.getComponent(Health.class).decrease(damage);
                getGameObject().destroy();
                attacked = true;
            }
        }

        if (!isDestroyed() && boxCollider.isCollidingTiles()) {
            getGameObject().destroy();
        }
    }
}
