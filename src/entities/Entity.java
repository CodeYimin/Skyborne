package entities;

import java.awt.Graphics;
import java.util.ArrayList;

import core.TimedUpdatable;
import graphics.Camera;
import graphics.Renderable;
import graphics.Sprite;
import scenes.World;
import util.ArrayListUtils;
import util.Size;
import util.Vector;
import world.Tile;

public abstract class Entity extends TimedUpdatable implements Renderable {
    private World world;

    private Vector position = Vector.ZERO;
    private Vector velocity = Vector.ZERO;
    private Vector direction = Vector.RIGHT_DIRECTION;
    private double gravity = 0;
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
        if (grounded && velocity.y() < 0) {
            // Prevent accelerating through the ground
            velocity = velocity.withY(0);
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

        // Test if entity is grounded
        Vector positionBelow = position.ceilY().subtractY(1);
        Hitbox hitboxBelow = new Hitbox(positionBelow, size);
        boolean collisionBelow = world.getCollidingTiles(hitboxBelow).size() > 0;
        if (collisionBelow) {
            grounded = true;
        } else {
            grounded = false;
        }

        // Apply gravity
        velocity = velocity.addY(-gravity * getDeltaTimeSecs());

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

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravityAcceleration) {
        this.gravity = gravityAcceleration;
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
