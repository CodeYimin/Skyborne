package components;

import java.awt.Color;
import java.awt.Graphics;

import structures.Bounds;
import structures.Vector;

public class HPBar extends Renderer {
    @Override
    public Bounds getRenderBounds() {
        Transform transform = getGameObject().getTransform();
        Health health = getGameObject().getComponent(Health.class);

        if (transform == null || health == null) {
            return null;
        }

        Vector position = transform.getPosition();
        Vector scale = transform.getScale();

        Vector barScale = new Vector(health.getMax() * 0.1, 0.1);
        Vector barPosition = position.addY(scale.getY() / 2 + 0.5).subtract(barScale.divide(2));

        return new Bounds(barPosition, barScale);
    }

    @Override
    public void render(Graphics g, Camera camera) {
        Health health = getGameObject().getComponent(Health.class);

        Bounds renderBounds = getRenderBounds();
        Vector fullBarPosition = renderBounds.getPosition();
        Vector fullBarSize = renderBounds.getSize();
        Vector barPosition = fullBarPosition;
        Vector barSize = fullBarSize.multiplyX((double) health.getCurrent() / health.getMax());

        g.setColor(Color.BLACK);
        g.fillRect((int) camera.worldToScreenPosition(fullBarPosition).getX(),
                (int) camera.worldToScreenPosition(fullBarPosition).getY(),
                (int) camera.worldToScreenSize(fullBarSize).getX(),
                (int) camera.worldToScreenSize(fullBarSize).getY());

        g.setColor(Color.RED);
        g.fillRect((int) camera.worldToScreenPosition(barPosition).getX(),
                (int) camera.worldToScreenPosition(barPosition).getY(),
                (int) camera.worldToScreenSize(barSize).getX(),
                (int) camera.worldToScreenSize(barSize).getY());
    }
}
