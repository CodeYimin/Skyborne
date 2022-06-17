package graphics;

import java.awt.Graphics;
import java.util.ArrayList;

import core.Drawable;
import core.GraphicsPanel;
import core.Updatable;
import entities.Entity;
import util.Size;
import util.Vector;

public class Camera implements Drawable, Updatable {
    private Vector position = Vector.ZERO;
    private GraphicsPanel graphicsPanel;
    private ArrayList<Renderable> renderables = new ArrayList<>();
    private double zoom;
    private Entity following;

    public Camera(GraphicsPanel graphicsPanel) {
        this.graphicsPanel = graphicsPanel;

        graphicsPanel.addDrawable(this);
    }

    @Override
    public void update() {
        if (following != null) {
            position = following.getPosition();
        }
    }

    @Override
    public void draw(Graphics g) {
        for (int i = 0; i < renderables.size(); i++) {
            renderables.get(i).render(g, this);
        }
    }

    public Vector worldToScreenPosition(Vector position) {
        Size screenSize = new Size(
                getGraphicsPanel().getWidth(),
                getGraphicsPanel().getHeight());
        Vector screenCenter = new Vector(screenSize.divide(2));

        Vector cameraDistToPosition = position.subtract(getPosition());
        Vector screenPosition = cameraDistToPosition
                .multiplyY(-1)
                .multiply(zoom)
                .add(screenCenter);

        return screenPosition;
    }

    public Vector getPosition() {
        return this.position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public double getZoom() {
        return this.zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

    public GraphicsPanel getGraphicsPanel() {
        return this.graphicsPanel;
    }

    public Entity getFollowing() {
        return following;
    }

    public void setFollowing(Entity following) {
        this.following = following;
    }

    public void addRenderable(Renderable renderable) {
        renderables.add(renderable);
    }

    public boolean removeRenderable(Renderable renderable) {
        return renderables.remove(renderable);
    }
}
