package components;

import java.awt.Graphics;
import java.util.ArrayList;

import core.Drawable;
import core.GameObject;
import core.GraphicsPanel;
import structures.Vector;

public class Camera extends Component implements Drawable {
    private Vector position;
    private double zoom;
    private GameObject following;

    public Camera(GameObject following) {
        this.position = Vector.ZERO;
        this.zoom = 100;
        this.following = following;
    }

    @Override
    public void start() {
        getGraphicsPanel().addDrawable(this);
    }

    @Override
    public void update(double deltaTime) {
        follow();
    }

    private void follow() {
        if (following == null) {
            return;
        }

        Transform followingTransform = following.getComponent(Transform.class);
        if (followingTransform == null) {
            return;
        }

        position = followingTransform.getPosition();
    }

    @Override
    public void draw(Graphics g) {
        ArrayList<Renderer> renderers = getRenderers();

        for (int i = 0; i < renderers.size(); i++) {
            renderers.get(i).render(g, this);
        }
    }

    public Vector worldToScreenPosition(Vector position) {
        Vector screenSize = new Vector(
                getGraphicsPanel().getWidth(),
                getGraphicsPanel().getHeight());
        Vector screenCenter = screenSize.divide(2);

        Vector cameraDistToPosition = position.subtract(getPosition());
        Vector screenPosition = cameraDistToPosition
                .multiplyY(-1)
                .multiply(zoom)
                .add(screenCenter);

        return screenPosition;
    }

    public Vector worldToScreenSize(Vector size) {
        return size.multiply(zoom);
    }

    public GraphicsPanel getGraphicsPanel() {
        return getGameObject().getScene().getGame().getWindow().getGraphicsPanel();
    }

    private ArrayList<Renderer> getRenderers() {
        return getGameObject().getScene().getComponents(Renderer.class);
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
