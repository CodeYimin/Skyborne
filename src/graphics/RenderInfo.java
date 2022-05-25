package graphics;

import entities.GameObject;
import util.Size;
import util.Vector;

public class RenderInfo {
    public final double zoom;
    public final Vector screenCenter;
    public final Size screenSize;
    public final Vector objectCameraOffset;
    public final Vector objectCenterScreenPos;

    public RenderInfo(GameObject objectToRender, Camera camera) {
        this.zoom = camera.getZoom();
        this.screenCenter = new Vector(
                camera.getGraphicsPanel().getWidth() / 2,
                camera.getGraphicsPanel().getHeight() / 2);
        this.screenSize = new Size(
                camera.getGraphicsPanel().getWidth(),
                camera.getGraphicsPanel().getHeight());
        this.objectCameraOffset = objectToRender.getPosition().subtract(camera.getPosition());
        this.objectCenterScreenPos = objectCameraOffset
                .multiply(new Vector(1, -1))
                .multiply(zoom)
                .add(screenCenter);
    }
}
