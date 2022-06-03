package entities;

import java.awt.Graphics;

import core.TimedUpdatable;
import graphics.Camera;
import graphics.Renderable;
import graphics.Sprite;
import scenes.World;
import util.Size;
import util.Vector;

public abstract class Entity extends TimedUpdatable implements Renderable {
    private World world;

    private Vector position = Vector.ZERO;
    private Vector velocity = Vector.ZERO;
    private Vector direction = Vector.RIGHT_DIRECTION;
    private double gravity = 0;
    private boolean grounded;
    private boolean phasesTiles = false;

    private Size size = new Size(1, 1);
    private Sprite sprite;

    public Entity(World world) {
        this.world = world;
    }

    public void onCollision(Entity other) {
        // Do nothing
    }

    public void onCollision(int tile) {
        // Do nothing
    }

    public boolean positionCollidesTile(Vector position) {
        Hitbox hitbox = new Hitbox(position, size);
        return world.getCollisionManager().getCollidingTiles(hitbox).size() > 0;
    }

    private void moveNoTilePhase() {
        if (grounded && velocity.y() < 0) {
            // Prevent accelerating through the ground
            velocity = velocity.withY(0);
        }

        Vector moveAmount = velocity.multiply(getDeltaTimeSecs());
        double xMoveAmount = moveAmount.x();
        double yMoveAmount = moveAmount.y();

        // Move X
        if (positionCollidesTile(position.addX(xMoveAmount))) {
            // Adjust position on collision to perfectly align with tile
            if (velocity.x() > 0) {
                // Right collision
                position = position.withX(Math.ceil(getHitbox().right()) - size.width() / 2);
            } else {
                // Left collision
                position = position.withX(Math.floor(getHitbox().left()) + size.width() / 2);
            }
        } else {
            position = position.addX(xMoveAmount);
        }

        // Move Y
        if (positionCollidesTile(position.addY(yMoveAmount))) {
            // Adjust position on collision to perfectly align with tile
            if (velocity.y() > 0) {
                // Top collision
                position = position.withY(Math.ceil(getHitbox().top()) - size.height());
            } else {
                // Bottom collision
                position = position.withY(Math.floor(getHitbox().bottom()));
            }
        } else {
            position = position.addY(yMoveAmount);
        }
    }

    public void move() {
        position = position.add(velocity.multiply(getDeltaTimeSecs()));
    }

    @Override
    public void update() {
        super.update();

        // Test if entity is grounded
        Vector positionBelow = position.ceilY().subtractY(1);
        Hitbox hitboxBelow = new Hitbox(positionBelow, size);
        boolean collisionBelow = world.getCollisionManager().getCollidingTiles(hitboxBelow).size() > 0;
        if (collisionBelow) {
            grounded = true;
        } else {
            grounded = false;
        }

        // Apply gravity
        velocity = velocity.addY(-gravity * getDeltaTimeSecs());

        // Move entity
        if (phasesTiles) {
            move();
        } else {
            moveNoTilePhase();
        }
    }

    @Override
    public void render(Graphics g, Camera camera) {
        if (sprite == null) {
            return;
        }

        // Bottom midpoint --> Top left corner
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
        return phasesTiles;
    }

    public void setPhasesTiles(boolean phasesTiles) {
        this.phasesTiles = phasesTiles;
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
