package components;

import core.GameObject;

public class StatOrb<T extends Stat> extends Component {
    private Class<T> statClass;
    private int value;

    public StatOrb(Class<T> statClass, int value) {
        this.statClass = statClass;
        this.value = value;
    }

    @Override
    public void update(double deltaTime) {
        BoxCollider boxCollider = getGameObject().getComponent(BoxCollider.class);
        if (boxCollider == null) {
            return;
        }

        for (GameObject gameObject : boxCollider.getCollidingObjects()) {
            T stat = gameObject.getComponent(statClass);
            if (!getGameObject().isDestroyed() && stat != null) {
                stat.increase(value);
                getGameObject().destroy();
            }
        }
    }
}
