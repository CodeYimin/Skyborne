package entities;

import java.util.ArrayList;

import core.Updatable;
import scenes.Level;
import world.Tile;

public class CollisionManager implements Updatable {
    private Creature creature;
    private ArrayList<CollisionListener> listeners;

    public CollisionManager(Creature creature) {
        this.creature = creature;
        this.listeners = new ArrayList<>();
    }

    @Override
    public void update() {
        for (CollisionListener listener : listeners) {
            for (Creature creature : getCollidingCreatures()) {
                listener.onCollision(creature);
            }
            for (Tile tile : getCollidingTiles()) {
                listener.onCollision(tile);
            }
        }
    }

    public ArrayList<Creature> getCollidingCreatures() {
        Level level = creature.getLevel();
        ArrayList<Creature> collidingCreatures = new ArrayList<Creature>();

        for (Creature otherCreature : level.getCreatures()) {
            if (otherCreature != creature) {
                if (getHitbox().intersects(otherCreature.getCollisionManager().getHitbox())) {
                    collidingCreatures.add(otherCreature);
                }
            }
        }

        return collidingCreatures;
    }

    public ArrayList<Tile> getCollidingTiles() {
        return creature.getLevel().getTilemap().getCollidingTiles(getHitbox());
    }

    public ArrayList<Tile> getIntersectingTiles() {
        return creature.getLevel().getTilemap().getIntersectingTiles(getHitbox());
    }

    public Hitbox getHitbox() {
        return new Hitbox(creature.getMovementManager().getPosition(), creature.getSize());
    }
}
