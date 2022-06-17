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

public class Creature implements Updatable, Renderable {
    private Level level;
    private MovementManager movementManager;
    private CollisionManager collisionManager;
    private Direction direction;
    private Size size;
    private AnimatedSprite sprite;
    // ArrayList<Weapon> weapons;

    public Creature(Level level, MovementController movementController) {
        this.level = level;
        this.movementManager = new MovementManager(movementController);
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
        Vector screenPosition = camera.worldToScreenPosition(getMovementManager().getPosition());
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
}
