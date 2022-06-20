package components;

import structures.Vector;
import util.Timer;

public class EnemyMotionController extends Component {
    private double speed;
    private Timer directionChangeTimer;

    public EnemyMotionController(double speed) {
        this.speed = speed;
        this.directionChangeTimer = new Timer(2500);
    }

    @Override
    public void update(double deltaTime) {
        Motion motion = getGameObject().getComponent(Motion.class);
        if (motion == null) {
            return;
        }

        if (directionChangeTimer.isDone()) {
            if (Math.random() < 0.25) {
                motion.setVelocity(Vector.ZERO);
            } else {
                Vector direction = Vector.random();
                motion.setVelocity(direction.multiply(speed));
            }
            directionChangeTimer.resetAndStart();
        }
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
