package entities;

import core.Updatable;
import util.Vector;

public abstract class GameObject implements Updatable {
    private Vector position = new Vector(0, 0);

    public GameObject() {
        // Do nothing
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }
}
