package util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class GraphicsUtils {
    public static void drawCenteredString(Graphics g, String text, int x, int y, Color color, float fontSize) {
        g.setFont(g.getFont().deriveFont(fontSize));
        Rectangle2D textBounds = g.getFontMetrics().getStringBounds(text, g);
        int textWidth = (int) textBounds.getWidth();
        int textHeight = (int) textBounds.getHeight();
        int textPositionX = x - textWidth / 2;
        int textPositionY = y + textHeight / 2 - 4;

        g.setColor(color);
        g.drawString(text, textPositionX, textPositionY);
    }
}
