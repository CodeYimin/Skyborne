package graphics.renderers;

import java.awt.Color;
import java.awt.Graphics2D;

import entities.GameObject;
import graphics.Camera;
import util.Size;
import util.Vector;

public class RectangleRenderer implements Renderer {
    private Size size;

    public RectangleRenderer(Size size) {
        this.size = size;
    }

    public void draw(Graphics2D g, Camera camera, GameObject objectToRender) {
        Size screenSize = size.multiply(camera.getZoom());
        Vector screenCenter = new Vector(
                camera.getGraphicsPanel().getWidth() / 2,
                camera.getGraphicsPanel().getHeight() / 2);

        Vector objectRelativeCameraPos = objectToRender.getPosition().subtract(camera.getPosition());
        Vector objectScreenPos = objectRelativeCameraPos
                .multiply(new Vector(1, -1))
                .multiply(camera.getZoom())
                .add(screenCenter)
                .subtract(new Vector(screenSize).multiply(0.5));

        g.setColor(Color.BLACK);
        g.fillRect(
                (int) objectScreenPos.getX(),
                (int) objectScreenPos.getY(),
                (int) screenSize.getWidth(),
                (int) screenSize.getHeight());
    }
}
