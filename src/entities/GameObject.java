package entities;

import java.awt.Graphics;

import collision.Collider;
import core.Updatable;
import graphics.Camera;
import graphics.renderers.RenderInfo;
import graphics.renderers.Renderer;
import util.Vector;

public abstract class GameObject implements Updatable {
    private Vector position;
    private Renderer renderer;
    private Collider collider;

    public GameObject() {
        this.position = new Vector(0, 0);
    }

    public GameObject(Vector position) {
        this.position = position;
    }

    public GameObject(Vector position, Renderer renderer) {
        this.position = position;
        this.renderer = renderer;
    }

    public GameObject(Vector position, Renderer renderer, Collider collider) {
        this.position = position;
        this.renderer = renderer;
        this.collider = collider;
    }

    public void render(Graphics g, Camera camera) {
        if (renderer != null) {
            renderer.render(g, new RenderInfo(this, camera));
        }
    }

    public boolean collidesWith(GameObject other) {
        if (collider == null) {
            return false;
        }

        return collider.collidesWith(other.getCollider());
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public Collider getCollider() {
        return collider;
    }

    public void setCollider(Collider collider) {
        this.collider = collider;
    }
}
