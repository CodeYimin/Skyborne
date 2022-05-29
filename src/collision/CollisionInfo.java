package collision;

public abstract class CollisionInfo {
    public final Collider collider;

    public CollisionInfo(Collider collider) {
        this.collider = collider;
    }
}
