package entities;

import java.awt.Graphics;
import java.util.ArrayList;

import core.TimedUpdatable;
import graphics.Camera;
import graphics.Renderable;
import graphics.Sprite;
import scenes.World;
import util.ArrayListUtils;
import util.Side;
import util.Size;
import util.Vector;
import world.Tile;

public abstract class Entity extends TimedUpdatable implements Renderable {
    private World world;

    private Vector position = Vector.ZERO;
    private Vector velocity = Vector.ZERO;
    private Vector direction = Vector.RIGHT_DIRECTION;
    private Vector acceleration = Vector.ZERO;
    private boolean grounded;
    private boolean phaseTiles = false;

    private Size size = new Size(1, 1);
    private Sprite sprite;

    public Entity(World world) {
        this.world = world;
    }

    public void onCollision(Entity other) {
        // Do nothing
    }

    public void onCollision(Tile tile) {
        // Do nothing
    }

    public void move(boolean phaseTiles) {
        if (!phaseTiles) {
            ArrayList<Side> tileCollidingSides = getTileCollidingSides();
            if (tileCollidingSides.contains(Side.TOP) && velocity.y() > 0) {
                velocity = velocity.withY(0);
            }
            if (tileCollidingSides.contains(Side.BOTTOM) && velocity.y() < 0) {
                velocity = velocity.withY(0);
            }
            if (tileCollidingSides.contains(Side.LEFT) && velocity.x() < 0) {
                velocity = velocity.withX(0);
            }
            if (tileCollidingSides.contains(Side.RIGHT) && velocity.x() > 0) {
                velocity = velocity.withX(0);
            }
        }

        ArrayList<Tile> collidingTiles = new ArrayList<>();
        ArrayList<Entity> collidingEntities = new ArrayList<>();

        Vector moveAmount = velocity.multiply(getDeltaTimeSecs());
        double xMoveAmount = moveAmount.x();
        double yMoveAmount = moveAmount.y();

        // Move X
        position = position.addX(xMoveAmount);
        ArrayList<Tile> collidingTilesX = world.getCollidingTiles(this);
        ArrayList<Entity> collidingEntitiesX = world.getCollidingEntities(this);

        if (collidingTilesX.size() > 0 && !phaseTiles) {
            if (xMoveAmount > 0) {
                // Right collision
                position = position.withX(Math.floor(getHitbox().right()) - size.width() / 2);
            } else {
                // Left collision
                position = position.withX(Math.ceil(getHitbox().left()) + size.width() / 2);
            }
        }

        // Move Y
        position = position.addY(yMoveAmount);
        ArrayList<Tile> collidingTilesY = world.getCollidingTiles(this);
        ArrayList<Entity> collidingEntitiesY = world.getCollidingEntities(this);

        if (collidingTilesY.size() > 0 && !phaseTiles) {
            if (yMoveAmount > 0) {
                // Top collision
                position = position.withY(Math.floor(getHitbox().top()) - size.height());
            } else {
                // Bottom collision
                position = position.withY(Math.ceil(getHitbox().bottom()));
            }
        }

        ArrayListUtils.addAllUnique(collidingTiles, collidingTilesX);
        ArrayListUtils.addAllUnique(collidingTiles, collidingTilesY);
        ArrayListUtils.addAllUnique(collidingEntities, collidingEntitiesX);
        ArrayListUtils.addAllUnique(collidingEntities, collidingEntitiesY);

        for (Entity entity : collidingEntities) {
            onCollision(entity);
        }
        for (Tile tile : collidingTiles) {
            onCollision(tile);
        }
    }

    @Override
    public void update() {
        super.update();

        // Apply acceleration
        velocity = velocity.add(acceleration.multiply(getDeltaTimeSecs()));

        // Move entity
        move(phaseTiles);
    }

    @Override
    public void render(Graphics g, Camera camera) {
        if (sprite == null) {
            return;
        }

        // Adjust from Bottom midpoint --> Top left corner
        Vector adjustedPosition = getPosition().subtractX(size.width() / 2).addY(size.height());
        Vector screenPosition = camera.worldToScreenPosition(adjustedPosition);

        sprite.render(g, screenPosition, size.multiply(camera.getZoom()));
    }

    public boolean collidesWith(Entity other) {
        return getHitbox().intersects(other.getHitbox());
    }

    public ArrayList<Side> getTileCollidingSides() {
        ArrayList<Side> tileCollidingSides = new ArrayList<>();

        // Top
        double yAbove = Math.floor(getHitbox().top());
        Hitbox hitboxAbove = new Hitbox(position.withY(yAbove), size);
        if (world.getCollidingTiles(hitboxAbove).size() > 0) {
            tileCollidingSides.add(Side.TOP);
        }

        // Bottom
        double yBelow = Math.ceil(getHitbox().bottom()) - 1;
        Hitbox hitboxBelow = new Hitbox(position.withY(yBelow), size);
        boolean collisionBelow = world.getCollidingTiles(hitboxBelow).size() > 0;
        if (collisionBelow) {
            tileCollidingSides.add(Side.BOTTOM);
        }

        // Left
        double xLeft = Math.ceil(getHitbox().left());
        Hitbox hitboxLeft = new Hitbox(position.withX(xLeft), size);
        if (world.getCollidingTiles(hitboxLeft).size() > 0) {
            tileCollidingSides.add(Side.LEFT);
        }

        // Right
        double xRight = Math.floor(getHitbox().right());
        Hitbox hitboxRight = new Hitbox(position.withX(xRight), size);
        if (world.getCollidingTiles(hitboxRight).size() > 0) {
            tileCollidingSides.add(Side.RIGHT);
        }

        return tileCollidingSides;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public Vector setAcceleration(Vector acceleration) {
        return this.acceleration = acceleration;
    }

    public Vector getAcceleration() {
        return acceleration;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Hitbox getHitbox() {
        return new Hitbox(getPosition(), size);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public boolean phasesTiles() {
        return phaseTiles;
    }

    public void setPhasesTiles(boolean phasesTiles) {
        this.phaseTiles = phasesTiles;
    }

    public World getLevel() {
        return world;
    }

    public void setLevel(World level) {
        this.world = level;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void destroy() {
        world.destroyEntity(this);
    }

    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }
}
