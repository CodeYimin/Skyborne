package collision;

public class Collision<T extends ColliderInfo> {
    public final T colliderInfo;
    public final ColliderInfo otherColliderInfo;

    public Collision(T colliderInfo, ColliderInfo otherColliderInfo) {
        this.colliderInfo = colliderInfo;
        this.otherColliderInfo = otherColliderInfo;
    }
}
