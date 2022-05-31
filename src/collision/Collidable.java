package collision;

public interface Collidable<T extends CollisionInfo> {
    public Collider<T> getCollider();

    public void onCollision(Collision<T> collision);
}
