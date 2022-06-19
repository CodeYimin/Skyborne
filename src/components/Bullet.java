package components;

import core.GameObject;

public class Bullet extends Component {
    private Class<? extends Component> targetClass;

    public Bullet(Class<? extends Component> targetClass) {
        this.targetClass = targetClass;
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
                collidingObject.getComponent(Health.class).damage(1);
                getGameObject().destroy();
                attacked = true;
            }
        }

        if (boxCollider.isCollidingTiles()) {
            getGameObject().destroy();
        }
    }
}
