package components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import util.Const;

public class PlayerStatsUI extends UI {
    public static final int WIDTH = Const.PLAYER_STATS_UI_WIDTH;
    public static final int HEIGHT = Const.PLAYER_STATS_UI_HEIGHT;
    public static final int MARGIN = Const.PLAYER_STATS_UI_MARGIN;
    public static final int PADDING = Const.PLAYER_STATS_UI_PADDING;
    public static final int MARGIN_BETWEEN_STATS = Const.PLAYER_STATS_UI_MARGIN_BETWEEN_STATS;

    public static final int INNER_WIDTH = WIDTH - 2 * PADDING;
    public static final int INNER_HEIGHT = HEIGHT - 2 * PADDING;
    public static final int NUM_STATS = 3;

    @Override
    public void draw(Graphics g) {
        Health health = getGameObject().getComponent(Health.class);
        Mana mana = getGameObject().getComponent(Mana.class);

        g.setColor(new Color(255, 255, 255, 100));
        g.fillRect(MARGIN, MARGIN, WIDTH, HEIGHT);
        g.setColor(Color.BLACK);
        ((Graphics2D) g).setStroke(new BasicStroke(5));
        g.drawRect(MARGIN, MARGIN, WIDTH, HEIGHT);

        if (health != null) {
            drawStat(g, 0, health.getCurrent(), health.getMax(), Color.RED);
        }
        if (mana != null) {
            drawStat(g, 1, mana.getCurrent(), mana.getMax(), Color.BLUE);
        }
    }

    private void drawStat(Graphics g, int statIndex, int value, int maxValue, Color color) {
        int statWidth = INNER_WIDTH;
        int statHeight = (INNER_HEIGHT - MARGIN_BETWEEN_STATS * (NUM_STATS - 1)) / NUM_STATS;

        int statPositionX = MARGIN + PADDING;
        int statPositionY = MARGIN + PADDING + statIndex * (statHeight + MARGIN_BETWEEN_STATS);

        g.setColor(Color.BLACK);
        g.fillRect(statPositionX, statPositionY, statWidth, statHeight);
        g.setColor(color);
        g.fillRect(statPositionX, statPositionY, statWidth * value / maxValue, statHeight);
        g.setColor(Color.BLACK);
        ((Graphics2D) g).setStroke(new BasicStroke(2));
        g.drawRect(statPositionX, statPositionY, statWidth, statHeight);

        g.setFont(g.getFont().deriveFont((float) statHeight));
        String statText = value + "/" + maxValue;
        Rectangle2D statTextBounds = g.getFontMetrics().getStringBounds(statText, g);
        int statTextWidth = (int) statTextBounds.getWidth();
        int statTextHeight = (int) statTextBounds.getHeight();
        int statTextPositionX = statPositionX + (statWidth - statTextWidth) / 2;
        int statTextPositionY = statPositionY + statHeight - (statHeight - statTextHeight) / 2 - 4;

        g.setColor(Color.WHITE);
        g.drawString(statText, statTextPositionX, statTextPositionY);
    }
}
