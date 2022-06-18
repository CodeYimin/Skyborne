package entities;

import java.util.ArrayList;

import components.Hitbox;
import components.Vector;
import core.Updatable;
import scenes.Level;
import world.Tile;

public class CollisionManager implements Updatable {
    private Entity entity;
    private ArrayList<CollisionListener> listeners;

    public CollisionManager(Entity entity) {
        this.entity = entity;
        this.listeners = new ArrayList<>();
    }

    @Override
    public void update() {
        for (CollisionListener listener : listeners) {
            for (Entity entity : getCollidingEntities()) {
                listener.onCollision(entity);
            }
            for (Tile tile : getCollidingTiles()) {
                listener.onCollision(tile);
            }
        }
    }

    public ArrayList<Entity> getCollidingEntities() {
        Level level = entity.getLevel();
        ArrayList<Entity> collidingEntities = new ArrayList<Entity>();

        for (Entity otherEntity : level.getEntities()) {
            if (otherEntity != entity) {
                if (getHitbox().intersects(otherEntity.getCollisionManager().getHitbox())) {
                    collidingEntities.add(otherEntity);
                }
            }
        }

        return collidingEntities;
    }

    public ArrayList<Tile> getCollidingTiles() {
        return entity.getLevel().getTilemap().getCollidingTiles(getHitbox());
    }

    public ArrayList<Tile> getIntersectingTiles() {
        return entity.getLevel().getTilemap().getIntersectingTiles(getHitbox());
    }

    public ArrayList<Tile> getCollidingTiles(Vector position) {
        return entity.getLevel().getTilemap().getCollidingTiles(getHitbox(position));
    }

    public ArrayList<Tile> getIntersectingTiles(Vector position) {
        return entity.getLevel().getTilemap().getIntersectingTiles(getHitbox(position));
    }

    public Hitbox getHitbox() {
        return getHitbox(entity.getPosition());
    }

    public Hitbox getHitbox(Vector position) {
        return new Hitbox(position, entity.getSize());
    }

    public void addListener(CollisionListener listener) {
        listeners.add(listener);
    }

    public void removeListener(CollisionListener listener) {
        listeners.remove(listener);
    }
}
