package components;

import java.awt.Color;
import java.awt.Graphics;

import structures.Vector;

public class UndiscoveredRoomOverlay extends Renderer {
    @Override
    public void render(Graphics g, Camera camera) {
        Room room = getGameObject().getComponent(Room.class);
        if (room == null || room.isEntered()) {
            return;
        }

        Transform transform = getGameObject().getTransform();

        int width = room.getWidth();
        int height = room.getHeight();
        Vector screenSize = camera.worldToScreenSize(new Vector(width, height));

        Vector centerScreenPosition = transform.getScreenPosition();
        Vector topLeftScreenPosition = centerScreenPosition.subtract(screenSize.divide(2));

        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect((int) topLeftScreenPosition.getX(), (int) topLeftScreenPosition.getY(),
                (int) screenSize.getX(), (int) screenSize.getY());
    }
}
