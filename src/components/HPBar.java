package components;

import java.awt.Color;
import java.awt.Graphics;

import structures.Vector;

public class HPBar extends Renderer {
    @Override
    public void render(Graphics g, Camera camera) {
        Transform transform = getGameObject().getTransform();
        Health health = getGameObject().getComponent(Health.class);

        Vector position = transform.getPosition();
        Vector scale = transform.getScale();

        Vector fullBarScale = new Vector(health.getMaxHealth() * 0.1, 0.1);
        Vector fullBarPosition = position.addY(scale.getY() / 2 + 0.5).subtract(fullBarScale.divide(2));
        Vector barScale = fullBarScale.multiplyX((double) health.getHealth() / health.getMaxHealth());
        Vector barPosition = fullBarPosition;

        g.setColor(Color.BLACK);
        g.fillRect((int) camera.worldToScreenPosition(fullBarPosition).getX(),
                (int) camera.worldToScreenPosition(fullBarPosition).getY(),
                (int) camera.worldToScreenSize(fullBarScale).getX(),
                (int) camera.worldToScreenSize(fullBarScale).getY());

        g.setColor(Color.RED);
        g.fillRect((int) camera.worldToScreenPosition(barPosition).getX(),
                (int) camera.worldToScreenPosition(barPosition).getY(),
                (int) camera.worldToScreenSize(barScale).getX(),
                (int) camera.worldToScreenSize(barScale).getY());
    }
}
