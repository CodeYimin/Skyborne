package components;

import java.awt.Color;
import java.awt.Graphics;

public class HealthUI extends UI {
    public static final int HP_SIZE = 30;
    public static final int HP_MARGIN = 10;

    @Override
    public void draw(Graphics g) {
        Health health = getGameObject().getComponent(Health.class);
        if (health == null) {
            return;
        }

        g.setColor(Color.BLACK);
        g.fillRect(HP_MARGIN - 6, HP_MARGIN - 6, HP_SIZE * health.getMaxHealth() + HP_MARGIN * (health.getMaxHealth() - 1) + 12, HP_SIZE + 12);
        g.setColor(Color.WHITE);
        g.fillRect(HP_MARGIN - 3, HP_MARGIN - 3, HP_SIZE * health.getMaxHealth() + HP_MARGIN * (health.getMaxHealth() - 1) + 6, HP_SIZE + 6);

        for (int i = 0; i < health.getMaxHealth(); i++) {
            int x = i * HP_SIZE + HP_MARGIN * (i + 1);
            int y = HP_MARGIN;
            int width = HP_SIZE;
            int height = HP_SIZE;
            Color color;
            if (i < health.getHealth()) {
                color = Color.RED;
            } else {
                color = Color.BLACK;
            }
            g.setColor(color);
            g.fillRect(x, y, width, height);
        }
    }
}
