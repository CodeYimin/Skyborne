package entities;

import java.awt.Graphics2D;

import graphics.Camera;
import graphics.renderers.Renderer;

public class RenderableObject extends GameObject {
    private Renderer renderer;

    public RenderableObject() {
        // Empty Constructor
    }

    public RenderableObject(Renderer renderer) {
        this.renderer = renderer;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public void render(Graphics2D g, Camera camera) {
        renderer.draw(g, camera, this);
    }
}
