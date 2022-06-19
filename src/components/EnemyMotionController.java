package components;

import structures.Vector;

public class EnemyMotionController extends Component {
    private double speed;

    public EnemyMotionController(double speed) {
        this.speed = speed;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void update(double deltaTime) {
        Transform transform = getGameObject().getComponent(Transform.class);
        Transform playerTransform = getGameObject().getScene().getGameObject(Player.class).getComponent(Transform.class);
        Motion motion = getGameObject().getComponent(Motion.class);

        if (transform == null || playerTransform == null || motion == null) {
            return;
        }

        Vector direction = playerTransform.getPosition().subtract(transform.getPosition());
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
