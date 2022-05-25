package graphics;

import entities.GameObject;
import util.Size;
import util.Vector;

public class RenderInfo {
    public final double zoom;
    public final Size screenSize;
    public final Vector screenCenter;
    public final Vector objectCameraOffset;
    public final Vector objectScreenPos;

    public RenderInfo(GameObject objectToRender, Camera camera) {
        this.zoom = camera.getZoom();
        this.screenSize = new Size(
                camera.getGraphicsPanel().getWidth(),
                camera.getGraphicsPanel().getHeight());
        this.screenCenter = new Vector(screenSize.divide(2));
        this.objectCameraOffset = objectToRender.getPosition().subtract(camera.getPosition());
        this.objectScreenPos = objectCameraOffset
                .multiply(new Vector(1, -1))
                .multiply(zoom)
                .add(screenCenter);
    }
}
