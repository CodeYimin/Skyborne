package components;

import java.awt.Graphics;
import java.util.ArrayList;

import core.Drawable;
import core.GameObject;

public class UICamera extends Component implements Drawable {
    @Override
    public void start() {
        getGameObject().getScene().getGame().getWindow().getGraphicsPanel().addDrawable(this);
    }

    @Override
    public void draw(Graphics g) {
        ArrayList<UI> uis = getUI();

        for (int i = 0; i < uis.size(); i++) {
            uis.get(i).draw(g);
        }
    }

    public ArrayList<UI> getUI() {
        ArrayList<UI> ui = new ArrayList<>();
        for (GameObject gameObject : getGameObject().getScene().getGameObjects()) {
            if (gameObject.getComponent(UI.class) != null) {
                ui.add(gameObject.getComponent(UI.class));
            }
        }
        return ui;
    }
}
