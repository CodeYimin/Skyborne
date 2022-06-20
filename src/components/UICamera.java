package components;

import java.awt.Graphics;
import java.util.ArrayList;

import core.Drawable;

public class UICamera extends Component implements Drawable {
    @Override
    public void start() {
        getGameObject().getScene().getGame().getWindow().getGraphicsPanel().addDrawable(this);
    }

    @Override
    public void draw(Graphics g) {
        ArrayList<UI> uis = getGameObject().getScene().getComponents(UI.class);

        for (int i = 0; i < uis.size(); i++) {
            if (uis.get(i).isVisible()) {
                uis.get(i).draw(g);
            }
        }
    }
}
