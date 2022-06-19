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
        Transform transform = getGameObject().getComponent(Transform.class);
        Transform targetTransform = target.getComponent(Transform.class);

        if (transform == null || targetTransform == null) {
            return;
        }

        Vector direction = targetTransform.getPosition().subtract(transform.getPosition());
        transform.setRotation(direction.toAngle());
    }
}
