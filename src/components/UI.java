package components;

import java.awt.Graphics;

public abstract class UI extends Component {
    private boolean visible = true;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public abstract void draw(Graphics g);
}
