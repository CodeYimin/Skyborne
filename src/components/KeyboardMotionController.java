package components;

import input.Keyboard;
import structures.Vector;

public class KeyboardMotionController extends Component {
    private int upKey;
    private int downKey;
    private int leftKey;
    private int rightKey;
    private double speed;

    public KeyboardMotionController(int upKey, int downKey, int leftKey, int rightKey, double speed) {
        this.upKey = upKey;
        this.downKey = downKey;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.speed = speed;
    }

    @Override
    public void update(double deltaTime) {
        Motion motion = getGameObject().getComponent(Motion.class);
        if (motion == null) {
            return;
        }

        Keyboard keyboard = getGameObject().getScene().getGame().getKeyboard();

        Vector velocity = Vector.ZERO;
        if (keyboard.isKeyDown(upKey)) {
            velocity = velocity.addY(1);
        }
        if (keyboard.isKeyDown(downKey)) {
            velocity = velocity.addY(-1);
        }
        if (keyboard.isKeyDown(leftKey)) {
            velocity = velocity.addX(-1);
        }
        if (keyboard.isKeyDown(rightKey)) {
            velocity = velocity.addX(1);
        }

        motion.setVelocity(velocity.normalized().multiply(speed));
    }
}
