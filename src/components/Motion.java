package components;

import structures.Vector;

public abstract class Motion extends Component {
    public static final int IDLE = 0;
    public static final int WALKING = 1;
    public static final int FROZEN = 2;

    private Vector velocity;
    private int state = IDLE;

    public Motion() {
        this.velocity = Vector.ZERO;
    }

    public Motion(Vector velocity) {
        this.velocity = velocity;
    }

    public abstract void move(double deltaTime);

    @Override
    public void update(double deltaTime) {
        if (state != FROZEN) {
            move(deltaTime);
        }
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
