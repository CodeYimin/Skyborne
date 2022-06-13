package collision;

import java.util.ArrayList;

import core.Updatable;
import entities.Entity;
import entities.Hitbox;
import world.Tile;
import world.Tilemap;

public class CollisionManager implements Updatable {
    private ArrayList<Entity> entities = new ArrayList<>();
    private Tilemap tilemap;

    public CollisionManager(Tilemap tilemap) {
        this.tilemap = tilemap;
    }

    @Override
    public void update() {
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            ArrayList<Entity> collidingEntities = getCollidingEntities(entity);
            for (Entity collidingEntity : collidingEntities) {
                entity.onCollision(collidingEntity);
            }

            ArrayList<Tile> collidingTiles = getCollidingTiles(entity);
            for (Tile collidingTile : collidingTiles) {
                entity.onCollision(collidingTile);
            }
        }
    }

    public ArrayList<Tile> getCollidingTiles(Hitbox hitbox) {
        return tilemap.getCollidingTiles(hitbox);
    }

    public ArrayList<Tile> getCollidingTiles(Entity entity) {
        return tilemap.getCollidingTiles(entity.getHitbox());
    }

    public ArrayList<Tile> getIntersectingTiles(Hitbox hitbox) {
        return tilemap.getIntersectingTiles(hitbox);
    }

    public ArrayList<Entity> getCollidingEntities(Entity entity) {
        ArrayList<Entity> collidingEntities = new ArrayList<>();
        for (Entity otherEntity : entities) {
            if (entity != otherEntity && entity.getHitbox().intersects(otherEntity.getHitbox())) {
                collidingEntities.add(otherEntity);
            }
        }
        return collidingEntities;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public boolean removeEntity(Entity entity) {
        return entities.remove(entity);
    }

    public Tilemap getTilemap() {
        return tilemap;
    }

    public void setTilemap(Tilemap tilemap) {
        this.tilemap = tilemap;
    }
}