package components;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import core.Drawable;
import core.GameObject;
import core.GraphicsPanel;
import structures.Bounds;
import structures.Vector;

public class Camera extends Component implements Drawable {
    private Vector position;
    private double zoom;
    private GameObject following;
    private GraphicsPanel graphicsPanel;

    public Camera(GameObject following) {
        this.position = Vector.ZERO;
        this.zoom = 100;
        this.following = following;
    }

    public Camera(GameObject following, double zoom) {
        this.position = Vector.ZERO;
        this.zoom = zoom;
        this.following = following;
    }

    @Override
    public void start() {
        graphicsPanel = getGameObject().getScene().getGame().getWindow().getGraphicsPanel();
        graphicsPanel.addDrawable(this);
    }

    @Override
    public void destroy() {
        super.destroy();
        if (graphicsPanel != null) {
            graphicsPanel.removeDrawable(this);
        }
    }

    private void follow() {
        if (following == null || following.isDestroyed()) {
            return;
        }

        position = following.getTransform().getPosition();
    }

    @Override
    public int getZIndex() {
        return 0;
    }

    @Override
    public void draw(Graphics g) {
        follow();

        ArrayList<Renderer> renderers = getGameObject().getScene().getComponents(Renderer.class);
        for (Renderer renderer : List.copyOf(renderers)) {
            if (!renderer.isDestroyed() && renderer.getRenderBounds().collides(getViewportBounds())) {
                renderer.render(g, this);
            }
        }
    }

    public Vector worldToScreenPosition(Vector position) {
        Vector screenSize = new Vector(
                graphicsPanel.getWidth(),
                graphicsPanel.getHeight());
        Vector screenCenter = screenSize.divide(2);

        Vector cameraDistToPosition = position.subtract(getPosition());
        Vector screenPosition = cameraDistToPosition
                .multiplyY(-1)
                .multiply(zoom)
                .add(screenCenter);

        return screenPosition;
    }

    public Vector screenToWorldPosition(Vector position) {
        Vector screenSize = new Vector(
                graphicsPanel.getWidth(),
                graphicsPanel.getHeight());
        Vector screenCenter = screenSize.divide(2);

        Vector cameraDistToPosition = position.subtract(screenCenter);
        Vector worldPosition = cameraDistToPosition
                .multiplyY(-1)
                .multiply(1 / zoom)
                .add(getPosition());

        return worldPosition;
    }

    public Vector getViewportSize() {
        return new Vector(
                graphicsPanel.getWidth() / zoom,
                graphicsPanel.getHeight() / zoom);
    }

    public Bounds getViewportBounds() {
        Vector viewportSize = getViewportSize();
        return new Bounds(getPosition(), viewportSize);
    }

    public Vector worldToScreenSize(Vector size) {
        return size.multiply(zoom);
    }

    public Vector screenToWorldSize(Vector size) {
        return size.divide(zoom);
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

    public GameObject getFollowing() {
        return this.following;
    }

    public void setFollowing(GameObject following) {
        this.following = following;
    }
}
