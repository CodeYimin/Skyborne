package components;

import core.GameObject;
import structures.Vector;

public class EnemyMotionController extends Component {
    private GameObject target;
    private double speed;

    public EnemyMotionController(GameObject target, double speed) {
        this.target = target;
        this.speed = speed;
    }

    @Override
    public void update(double deltaTime) {
        Transform transform = getGameObject().getTransform();
        Transform targetTransform = target.getTransform();
        Motion motion = getGameObject().getComponent(Motion.class);

        if (transform == null || targetTransform == null || motion == null) {
            return;
        }

        if (transform.getPosition().distance(targetTransform.getPosition()) < 1) {
            motion.setVelocity(Vector.ZERO);
            return;
        }

        Vector direction = targetTransform.getPosition().subtract(transform.getPosition());
        double angle = direction.toAngle();
        double roundedAngle = Math.round(angle / (Math.PI / 4)) * (Math.PI / 4);
        Vector roundedDirection = new Vector(roundedAngle);
        motion.setVelocity(roundedDirection.normalized().multiply(speed));
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
