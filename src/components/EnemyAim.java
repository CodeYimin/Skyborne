package components;

import core.GameObject;
import structures.Vector;

public class EnemyAim extends Component {
    private GameObject target;

    public EnemyAim(GameObject target) {
        this.target = target;
    }

    @Override
    public void update(double deltaTime) {
        if (target == null || target.isDestroyed()) {
            return;
        }

        Transform transform = getGameObject().getTransform();
        Transform targetTransform = target.getTransform();

        Vector direction = targetTransform.getPosition().subtract(transform.getPosition());
        transform.setRotation(direction.toAngle());
    }
}
