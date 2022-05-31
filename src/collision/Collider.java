package collision;

public interface Collider<T extends CollisionInfo> {
    public T getCollisionInfo(Collider<?> with);
}
