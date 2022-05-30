package collision;

import entities.GameObject;

public abstract class ColliderInfo {
    public final GameObject gameObject;

    public ColliderInfo(GameObject gameObject) {
        this.gameObject = gameObject;
    }
}
