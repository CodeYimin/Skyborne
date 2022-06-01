package entities;

import core.Updatable;
import util.Vector;

public abstract class GameObject implements Updatable {
    private Vector position = new Vector(0, 0);
    private long lastUpdateTime = 0;
    private long deltaTime = 0;

    public GameObject() {
        // Do nothing
    }

    @Override
    public void update() {
        if (lastUpdateTime == 0) {
            lastUpdateTime = System.currentTimeMillis();
        }
        deltaTime = System.currentTimeMillis() - lastUpdateTime;
        lastUpdateTime = System.currentTimeMillis();
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public double getDeltaTime() {
        return deltaTime;
    }

    public double getDeltaTimeSecs() {
        return deltaTime / 1000.0;
    }
}
