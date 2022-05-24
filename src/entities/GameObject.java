package entities;

import util.Vector;

public class GameObject {
    private Vector position;

    public GameObject() {
        this.position = new Vector(0, 0);
    }

    public GameObject(Vector position) {
        this.position = position;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public void update() {
        // Do nothing
    }
}
