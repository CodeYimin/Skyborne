package entities;

import java.awt.Graphics;
import java.util.ArrayList;

import core.Updatable;
import graphics.Camera;
import graphics.Renderable;
import graphics.Sprite;
import scenes.World;
import util.Side;
import util.Size;
import util.Vector;
import world.TileCollision;

public abstract class Entity extends Updatable implements Renderable {
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

    public void onCollision(TileCollision collision) {
        // Do nothing
    }

    public boolean positionWillIntesectTiles(Vector position) {
        Hitbox hitbox = new Hitbox(position, size);
        return world.getCollisionManager().getIntersectingTiles(hitbox).size() > 0;
    }

    public void move(boolean phaseTiles) {
        Vector moveAmount = velocity.multiply(getDeltaTimeSecs());
        double xMoveAmount = moveAmount.x();
        double yMoveAmount = moveAmount.y();

        // Move X
        if (!phaseTiles && positionWillIntesectTiles(position.addX(xMoveAmount))) {
            // Adjust position on collision to perfectly align with tile
            if (velocity.x() > 0) {
                // Right collision
                setRight(Math.ceil(getRight()));
            } else {
                // Left collision
                setLeft(Math.floor(getLeft()));
            }

            // Reset velocity since it hit a wall
            velocity = velocity.withX(0);
        } else {
            position = position.addX(xMoveAmount);
        }

        // Move Y
        if (!phaseTiles && positionWillIntesectTiles(position.addY(yMoveAmount))) {
            // Adjust position on collision to perfectly align with tile
            if (velocity.y() > 0) {
                // Top collision
                setTop(Math.ceil(getTop()));
            } else {
                // Bottom collision
                setBottom(Math.floor(getBottom()));
            }

            // Reset velocity since it hit a wall
            velocity = velocity.withY(0);
        } else {
            position = position.addY(yMoveAmount);
        }
    }

    @Override
    public void update() {
        super.update();

        // Apply acceleration
        velocity = velocity.add(acceleration.multiply(getDeltaTimeSecs()));

        // Move entity
        move(false);
    }

    @Override
    public void render(Graphics g, Camera camera) {
        if (sprite == null) {
            return;
        }

        // Adjust from Center --> Top left corner
        Vector adjustedPosition = getPosition().subtractX(size.width() / 2).addY(size.height() / 2);
        Vector screenPosition = camera.worldToScreenPosition(adjustedPosition);

        sprite.render(g, screenPosition, size.multiply(camera.getZoom()));
    }

    public boolean collidesWith(Entity other) {
        return getHitbox().intersects(other.getHitbox());
    }

    public ArrayList<Side> getSidesAlignedWithSolid() {
        ArrayList<Side> sides = new ArrayList<>();

        ArrayList<TileCollision> tileCollisions = world.getCollisionManager().getTileCollisions(getHitbox());
        for (TileCollision tileCollision : tileCollisions) {
            Side side = tileCollision.getSideAlignedWithTile();
            if (side != null && !sides.contains(side) && tileCollision.getTile().isSolid()) {
                sides.add(side);
            }
        }

        return sides;
    }

    public ArrayList<Side> getSidesAlignedWithTile() {
        ArrayList<Side> sides = new ArrayList<>();

        ArrayList<TileCollision> tileCollisions = world.getCollisionManager().getTileCollisions(getHitbox());
        for (TileCollision tileCollision : tileCollisions) {
            Side side = tileCollision.getSideAlignedWithTile();
            if (side != null && !sides.contains(side)) {
                sides.add(side);
            }
        }

        return sides;
    }

    public Vector getPosition() {
        return position;
    }

    public double getX() {
        return position.x();
    }

    public double getY() {
        return position.y();
    }

    public double getTop() {
        return getHitbox().top();
    }

    public double getBottom() {
        return getHitbox().bottom();
    }

    public double getLeft() {
        return getHitbox().left();
    }

    public double getRight() {
        return getHitbox().right();
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public void setX(double x) {
        position = position.withX(x);
    }

    public void setY(double y) {
        position = position.withY(y);
    }

    public void setTop(double y) {
        setY(y - size.height() / 2);
    }

    public void setBottom(double y) {
        setY(y + size.height() / 2);
    }

    public void setLeft(double x) {
        setX(x + size.width() / 2);
    }

    public void setRight(double x) {
        setX(x - size.width() / 2);
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
