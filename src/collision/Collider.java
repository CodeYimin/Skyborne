package collision;

import entities.GameObject;

public abstract class Collider {
    private GameObject owner;

    public Collider(GameObject owner) {
        this.owner = owner;
    }

    public GameObject getOwner() {
        return owner;
    }

    public abstract boolean collidesWith(Collider other);
}
