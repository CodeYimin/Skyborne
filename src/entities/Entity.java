package entities;

import java.awt.Graphics;

import core.UpdateInfo;
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
    private double maxDownwardSpeed = 8;
    private Vector velocity = new Vector(0, 0);
    private Vector acceleration = new Vector(0, 0);

    private Size size = new Size(1, 1);
    private Sprite sprite;

    public Entity(Level level) {
        this.level = level;
    }

    private void move(double deltaTimeSeconds) {
        Vector deltaVelocity = velocity.multiply(deltaTimeSeconds);
        Vector deltaVelocityX = new Vector(deltaVelocity.getX(), 0);
        Vector deltaVelocityY = new Vector(0, deltaVelocity.getY());

        if (level.getCollidingTiles(getPosition().add(deltaVelocityX), size).size() == 0) {
            setPosition(getPosition().add(deltaVelocityX));
        } else {
            if (velocity.getX() > 0) {
                setPosition(getPosition().withX(Math.ceil(getPosition().getX()) - size.getWidth() / 2));
            } else {
                setPosition(getPosition().withX(Math.floor(getPosition().getX()) + size.getWidth() / 2));
            }
        }

        if (level.getCollidingTiles(getPosition().add(deltaVelocityY), size).size() == 0) {
            setPosition(getPosition().add(deltaVelocityY));
        } else {
            if (velocity.getY() < 0) {
                setPosition(getPosition().withY(Math.floor(getPosition().getY())));
            } else {
                setPosition(getPosition().withY(Math.ceil(getPosition().getY() + size.getHeight()) - size.getHeight()));
            }
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
                .subtract(new Vector(size.getWidth() / 2, size.getHeight()).multiply(camera.getZoom()));

        sprite.render(g, adjustedScreenPosition, camera.getZoom());
    }

    public boolean collidesWith(Entity other) {
        Vector topLeft = getPosition()
                .add(new Vector(-getSize().getWidth() / 2, getSize().getHeight()));
        Vector botRight = getPosition().add(new Vector(getSize().getWidth() / 2, 0));

        Vector otherTopLeft = other.getPosition()
                .add(new Vector(-other.getSize().getWidth() / 2, other.getSize().getHeight()));
        Vector otherBotRight = other.getPosition().add(new Vector(other.getSize().getWidth() / 2, 0));

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

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
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
