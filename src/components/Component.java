package components;

import core.GameObject;

public abstract class Component {
    private GameObject gameObject = null;

    public void start() {
        // Do nothing
    }

    public void update(double deltaTime) {
        // Do nothing
    }

    public void stop() {
        // Do nothing
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }
}
