package entities;

import java.awt.Graphics;

import core.Updatable;
import graphics.AnimatedSprite;
import graphics.Camera;
import graphics.Renderable;
import scenes.Level;
import util.Direction;
import util.Size;
import util.Vector;

public class Entity implements Updatable, Renderable {
    private Level level;

    private AnimatedSprite sprite;
    private Vector position;
    private Direction direction;
    private Size size;

    private MovementManager movementManager;
    private CollisionManager collisionManager;

    public Entity(Level level, MovementController movementController) {
        this.level = level;
        this.movementManager = new MovementManager(this, movementController);
        this.collisionManager = new CollisionManager(this);
        this.size = new Size(1, 1);

        level.getCamera().addRenderable(this);
    }

    @Override
    public void update() {
        movementManager.update();
        collisionManager.update();
    }

    @Override
    public void render(Graphics g, Camera camera) {
        Vector screenPosition = camera.worldToScreenPosition(position);
        Size screenSize = getSize().multiply(camera.getZoom());

        sprite.draw(g, screenPosition, screenSize, getDirection() == Direction.LEFT);
    }

    public Level getLevel() {
        return level;
    }

    public MovementManager getMovementManager() {
        return movementManager;
    }

    public CollisionManager getCollisionManager() {
        return collisionManager;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public AnimatedSprite getSprite() {
        return sprite;
    }

    public void setSprite(AnimatedSprite sprite) {
        this.sprite = sprite;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public double getRight() {
        return getCollisionManager().getHitbox().right();
    }

    public double getLeft() {
        return getCollisionManager().getHitbox().left();
    }

    public double getTop() {
        return getCollisionManager().getHitbox().top();
    }

    public double getBottom() {
        return getCollisionManager().getHitbox().bottom();
    }

    public void setRight(double right) {
        setPosition(getPosition().withX(right - size.width() / 2));
    }

    public void setLeft(double left) {
        setPosition(getPosition().withX(left + size.width() / 2));
    }

    public void setTop(double top) {
        setPosition(getPosition().withY(top - size.height() / 2));
    }

    public void setBottom(double bottom) {
        setPosition(getPosition().withY(bottom + size.height() / 2));
    }
}
