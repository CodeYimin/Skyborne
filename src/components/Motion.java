package components;

import structures.Vector;

public abstract class Motion extends Component {
    private Vector velocity;

    public Motion() {
        this.velocity = Vector.ZERO;
    }

    public Motion(Vector velocity) {
        this.velocity = velocity;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }
}
