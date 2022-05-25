package graphics.renderers;

import java.awt.Color;
import java.awt.Graphics2D;

import graphics.RenderInfo;
import util.Size;
import util.Vector;

public class RectangleRenderer implements Renderer {
    private Size size;

    public RectangleRenderer(Size size) {
        this.size = size;
    }

    public void render(Graphics2D g, RenderInfo renderInfo) {
        Size rectScreenSize = size.multiply(renderInfo.zoom);
        Vector objectScreenPos = renderInfo.objectCenterScreenPos
                .subtract(new Vector(rectScreenSize).multiply(0.5));

        g.setColor(Color.BLACK);
        g.fillRect(
                (int) objectScreenPos.getX(),
                (int) objectScreenPos.getY(),
                (int) rectScreenSize.getWidth(),
                (int) rectScreenSize.getHeight());
    }
}
