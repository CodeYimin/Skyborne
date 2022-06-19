package components;

import java.awt.Color;
import java.awt.Graphics;

public class HealthUI extends UI {
    @Override
    public void draw(Graphics g) {
        Health health = getGameObject().getComponent(Health.class);

        g.setColor(Color.BLACK);
        g.fillRect(50, 10, 100, 50);

        g.setColor(Color.RED);
        g.setFont(g.getFont().deriveFont(40f));
        g.drawString("HP: " + health.getHealth(), 50, 50);
    }
}
