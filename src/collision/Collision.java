package collision;

public class Collision<T extends CollisionInfo> {
    public final T collisionInfo;
    public final CollisionInfo otherCollisionInfo;

    public Collision(T colliderInfo, CollisionInfo otherColliderInfo) {
        this.collisionInfo = colliderInfo;
        this.otherCollisionInfo = otherColliderInfo;
    }
}
