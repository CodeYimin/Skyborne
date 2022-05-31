package entities;

import java.awt.Graphics;

import core.UpdateInfo;
import graphics.Camera;
import graphics.Renderable;
import graphics.Sprite;
import scenes.Level;
import util.Vector;

public abstract class Entity extends GameObject implements Renderable {
    private Level level;

    private boolean grounded;
    private double speed = 5;
    private double maxDownwardSpeed = 8;
    private Vector velocity = new Vector(0, 0);
    private Vector acceleration = new Vector(0, 0);

    private Vector size = new Vector(1, 1);
    private Sprite sprite;

    public Entity(Level level) {
        this.level = level;
    }

    private void move(double deltaTimeSeconds) {
        Vector deltaVelocity = velocity.multiply(deltaTimeSeconds);
        Vector deltaVelocityX = new Vector(deltaVelocity.getX(), 0);
        Vector deltaVelocityY = new Vector(0, deltaVelocity.getY());

        // Move X Axis
        setPosition(getPosition().add(deltaVelocityX));
        if (level.getCollidingTiles(this).size() > 0) {
            setPosition(getPosition().subtract(deltaVelocityX));
        }

        // Move Y Axis
        setPosition(getPosition().add(deltaVelocityY));
        if (level.getCollidingTiles(this).size() > 0) {
            if (velocity.getY() < 0) {
                grounded = true;
            }

            setPosition(getPosition().subtract(deltaVelocityY));
        } else {
            grounded = false;
        }
    }

    @Override
    public void update(UpdateInfo updateInfo) {
        velocity = velocity.add(acceleration.multiply(updateInfo.deltaTimeSeconds));

        if (velocity.getY() < -maxDownwardSpeed) {
            velocity = new Vector(velocity.getX(), -maxDownwardSpeed);
        }

        move(updateInfo.deltaTimeSeconds);
    }

    @Override
    public void render(Graphics g, Camera camera) {
        if (sprite == null) {
            return;
        }

        Vector screenPosition = camera.getScreenPosition(this);
        Vector adjustedScreenPosition = screenPosition
                .subtract(new Vector(size.getX() / 2, size.getY()).multiply(camera.getZoom()));

        sprite.render(g, adjustedScreenPosition, camera.getZoom());
    }

    public boolean collidesWith(Entity other) {
        Vector topLeft = getPosition()
                .add(new Vector(-getSize().getX() / 2, getSize().getY()));
        Vector botRight = getPosition().add(new Vector(getSize().getX() / 2, 0));

        Vector otherTopLeft = other.getPosition()
                .add(new Vector(-other.getSize().getX() / 2, other.getSize().getY()));
        Vector otherBotRight = other.getPosition().add(new Vector(other.getSize().getX() / 2, 0));

        return topLeft.getX() < otherBotRight.getX() &&
                botRight.getX() > otherTopLeft.getX() &&
                topLeft.getY() < otherBotRight.getY() &&
                botRight.getY() > otherTopLeft.getY();
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

    public Vector getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector acceleration) {
        this.acceleration = acceleration;
    }

    public Vector getSize() {
        return size;
    }

    public void setSize(Vector size) {
        this.size = size;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
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
