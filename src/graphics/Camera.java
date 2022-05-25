package graphics;

import java.awt.Graphics2D;
import java.util.ArrayList;

import core.UpdateInfo;
import entities.GameObject;
import entities.RenderableObject;

public class Camera extends GameObject implements Drawable {
    private GraphicsPanel graphicsPanel;
    private ArrayList<GameObject> gameObjects;
    private double zoom;

    public double getZoom() {
        return this.zoom;
    }

    public GraphicsPanel getGraphicsPanel() {
        return this.graphicsPanel;
    }

    public Camera(GraphicsPanel graphicsPanel, ArrayList<GameObject> gameObjects, double zoom) {
        this.graphicsPanel = graphicsPanel;
        this.gameObjects = gameObjects;
        this.zoom = zoom;

        graphicsPanel.addDrawable(this);
    }

    @Override
    public void update(UpdateInfo updateInfo) {
        // Do nothing
    }

    @Override
    public void draw(Graphics2D g) {
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof RenderableObject) {
                ((RenderableObject) gameObject).render(g, this);
            }
        }
    }
}
