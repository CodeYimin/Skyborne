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

    public void destroy() {
        if (isDestroyed()) {
            return;
        }

        gameObject.removeComponent(this);
    }

    public boolean isDestroyed() {
        return gameObject == null || gameObject.isDestroyed();
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }
}
