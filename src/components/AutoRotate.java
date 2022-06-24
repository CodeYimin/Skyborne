package components;

import core.GameObject;
import structures.Vector;

/**
 * Automatically rotates the game object this component is attached to in the
 * direction of the target
 * 
 * @author Yimin Sun
 */
public class AutoRotate extends Component {
    private GameObject target;

    public AutoRotate(GameObject target) {
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
