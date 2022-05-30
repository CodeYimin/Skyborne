package collision;

public interface Collidable<T extends ColliderInfo> {
    public Collision<T> getCollision(Collidable<?> other);

    public void onCollision(Collision<T> collision);
}
