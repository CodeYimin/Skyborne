package graphics;

import java.awt.Graphics;
import java.util.ArrayList;

import core.Drawable;
import core.GraphicsPanel;
import core.UpdateInfo;
import entities.Entity;
import entities.GameObject;
import util.Size;
import util.Vector;

public class Camera extends GameObject implements Drawable {
    private Vector position = new Vector(0, 0);
    private GraphicsPanel graphicsPanel;
    private ArrayList<Renderable> renderables = new ArrayList<>();
    private double zoom;
    private Entity following;

    public Camera(GraphicsPanel graphicsPanel) {
        this.graphicsPanel = graphicsPanel;

        graphicsPanel.addDrawable(this);
    }

    @Override
    public void update(UpdateInfo updateInfo) {
        if (following != null) {
            position = following.getPosition();
        }
    }

    @Override
    public void draw(Graphics g) {
        for (Renderable renderable : renderables) {
            renderable.render(g, this);
        }
    }

    public Vector getScreenPosition(GameObject gameObject) {
        Size screenSize = new Size(
                getGraphicsPanel().getWidth(),
                getGraphicsPanel().getHeight());
        Vector screenCenter = new Vector(screenSize.divide(2));
        Vector objectCameraOffset = gameObject.getPosition().subtract(getPosition());
        Vector objectScreenPos = objectCameraOffset
                .multiply(new Vector(1, -1))
                .multiply(zoom)
                .add(screenCenter);

        return objectScreenPos;
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

    public void setFollowing(Entity gameObject) {
        this.following = gameObject;
    }

    public void addRenderable(Renderable renderable) {
        renderables.add(renderable);
    }
}
