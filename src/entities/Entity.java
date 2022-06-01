package entities;

import java.awt.Graphics;

import graphics.Camera;
import graphics.Renderable;
import graphics.Sprite;
import scenes.Level;
import util.Size;
import util.Vector;

public abstract class Entity extends GameObject implements Renderable {
    private Level level;

    private boolean grounded;
    private double speed = 5;
    private Vector velocity = new Vector(0, 0);
    private double gravity = 0;
    private boolean antiTileCollision = false;

    private Size size = new Size(1, 1);
    private Sprite sprite;

    public Entity(Level level) {
        this.level = level;
    }

    public void onCollision(Entity other) {
        // Do nothing
    }

    public void onCollision(int tile) {
        // Do nothing
    }

    private void move() {
        if (antiTileCollision && grounded && velocity.y() < 0) {
            // Prevent accelerating through tile below when antiTileCollision enabled
            velocity = velocity.withY(0);
        }

        Vector moveAmount = velocity.multiply(getDeltaTimeSecs());

        if (!antiTileCollision) {
            setPosition(getPosition().add(moveAmount));
            return;
        }

        // Split movement into x and y component for detecting collision on individual
        // axises
        double xMoveAmount = moveAmount.x();
        double yMoveAmount = moveAmount.y();

        // Move X
        Vector xMovePosition = getPosition().addX(xMoveAmount);
        Hitbox xMoveHitbox = new Hitbox(xMovePosition, size);
        boolean xMoveCollision = level.getCollidingTiles(xMoveHitbox).size() > 0;
        if (xMoveCollision) {
            // Adjust position on collision to perfectly align with tile
            if (velocity.x() > 0) {
                // Right collision
                setPosition(getPosition().withX(Math.ceil(getHitbox().right()) - size.width()
                        / 2));
            } else {
                // Left collision
                setPosition(getPosition().withX(Math.floor(getHitbox().left()) + size.width()
                        / 2));
            }
        } else {
            setPosition(xMovePosition);
        }

        Vector yMovePosition = getPosition().addY(yMoveAmount);
        Hitbox yMoveHitbox = new Hitbox(yMovePosition, size);
        boolean yMoveCollision = level.getCollidingTiles(yMoveHitbox).size() > 0;
        if (yMoveCollision) {
            // Adjust position on collision to perfectly align with tile
            if (velocity.y() > 0) {
                // Top collision
                setPosition(getPosition().withY(Math.ceil(getHitbox().top()) -
                        size.height()));
            } else {
                // Bottom collision
                setPosition(getPosition().withY(Math.floor(getHitbox().bottom())));
            }
        } else {
            setPosition(yMovePosition);
        }
    }

    @Override
    public void update() {
        super.update();

        // Test if entity is grounded
        Vector positionBelow = getPosition().ceilY().subtractY(1);
        Hitbox hitboxBelow = new Hitbox(positionBelow, size);
        boolean collisionBelow = level.getCollidingTiles(hitboxBelow).size() > 0;
        if (collisionBelow) {
            grounded = true;
        } else {
            grounded = false;
        }

        // Apply gravity
        velocity = velocity.addY(-gravity * getDeltaTimeSecs());

        // Move entity
        move();
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

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
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

    public boolean isAntiTileCollision() {
        return antiTileCollision;
    }

    public void setAntiTileCollision(boolean antiTileCollision) {
        this.antiTileCollision = antiTileCollision;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public boolean isGrounded() {
        return grounded;
    }
}
