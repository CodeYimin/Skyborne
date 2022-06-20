package components;

import java.awt.Color;
import java.awt.Graphics;

import core.GraphicsPanel;
import events.EventListener;
import events.HealthEvent;
import util.Timer;

public class DamageOverlayUI extends UI {
    private Timer displayTimer = new Timer(100);

    @Override
    public void start() {
        setVisible(false);
        displayTimer.resetAndStop();

        Health health = getGameObject().getComponent(Health.class);
        if (health == null) {
            return;
        }

        health.getEventManager().addListener(new EventListener<HealthEvent>(0) {
            @Override
            public void onEvent(HealthEvent event) {
                if (event.getOldHealth() > event.getNewHealth() && !displayTimer.isStarted()) {
                    displayTimer.resetAndStart();
                    DamageOverlayUI.this.setVisible(true);
                }
            }
        });
    }

    @Override
    public void update(double deltaTime) {
        if (displayTimer.isDone()) {
            setVisible(false);
            displayTimer.resetAndStop();
        }
    }

    @Override
    public void draw(Graphics g) {
        GraphicsPanel graphicsPanel = getGameObject().getScene().getGame().getWindow().getGraphicsPanel();
        int width = graphicsPanel.getWidth();
        int height = graphicsPanel.getHeight();

        g.setColor(new Color(255, 0, 0, (int) (100 * displayTimer.getPercentage())));
        g.fillRect(0, 0, width, height);
    }
}
