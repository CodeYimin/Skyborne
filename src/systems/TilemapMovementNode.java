package systems;

import components.Transform;
import components.Vector;
import world.Tilemap;

public class TilemapMovementNode implements SystemNode {
    private final Tilemap tilemap;
    private final Transform transform;
    private final Vector velocity;

    public TilemapMovementNode(Tilemap tilemap, Transform transform, Vector velocity) {
        this.tilemap = tilemap;
        this.transform = transform;
        this.velocity = velocity;
    }

    public Tilemap getTilemap() {
        return tilemap;
    }

    public Transform getTransform() {
        return transform;
    }

    public Vector getVelocity() {
        return velocity;
    }
}
