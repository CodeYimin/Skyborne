package entities;

import world.Tile;

public interface CollisionListener {
    public void onCollision(Tile tile);

    public void onCollision(Entity creature);
}
