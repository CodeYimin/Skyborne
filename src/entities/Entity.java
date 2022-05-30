package entities;

import java.awt.Graphics;

import core.UpdateInfo;
import graphics.Camera;
import graphics.Renderable;
import graphics.Sprite;
import util.Vector;
import world.TileMap;

public abstract class Entity extends GameObject implements Renderable {
    private double speed = 5;
    private Vector velocity = new Vector(0, 0);
    private Vector acceleration = new Vector(0, 0);
    private Vector size = new Vector(1, 1);
    private Sprite sprite;
    private TileMap tileMap;

    public Entity() {
        // Do nothing
    }

    private void move(double deltaTimeSeconds) {
        Vector deltaVelocity = velocity.multiply(deltaTimeSeconds);
        setPosition(getPosition().add(deltaVelocity));
        if (tileMap != null && tileMap.getCollidingTiles(this).size() > 0) {
            setPosition(getPosition().subtract(deltaVelocity));
        }
    }

    @Override
    public void update(UpdateInfo updateInfo) {
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

    public TileMap getTileMap() {
        return tileMap;
    }

    public void setTileMap(TileMap tileMap) {
        this.tileMap = tileMap;
    }
}
