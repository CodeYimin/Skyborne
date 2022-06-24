package components;

import java.awt.Graphics;
import java.util.ArrayList;

import core.Drawable;
import core.GraphicsPanel;
import util.ArrayUtils;

public class UICamera extends Component implements Drawable {
    private GraphicsPanel graphicsPanel;

    @Override
    public void start() {
        graphicsPanel = getGameObject().getScene().getGame().getWindow().getGraphicsPanel();
        graphicsPanel.addDrawable(this);
    }

    @Override
    public void destroy() {
        if (graphicsPanel != null) {
            graphicsPanel.removeDrawable(this);
        }
    }

    @Override
    public int getZIndex() {
        return 100;
    }

    @Override
    public void draw(Graphics g) {
        if (isDestroyed()) {
            return;
        }

        ArrayList<UI> uis = getGameObject().getScene().getComponents(UI.class);
        for (UI ui : ArrayUtils.copyOf(uis)) {
            if (ui.isVisible() && !ui.isDestroyed()) {
                ui.draw(g);
            }
        }
    }
}
