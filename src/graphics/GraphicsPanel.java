package graphics;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GraphicsPanel extends JPanel {
    private final ArrayList<Drawable> drawables = new ArrayList<>();

    public GraphicsPanel() {

    }

    public void addDrawable(Drawable drawable) {
        drawables.add(drawable);
    }

    public void removeDrawable(Drawable drawable) {
        drawables.remove(drawable);
    }

    public void clearDrawables() {
        drawables.clear();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Drawable drawable : drawables) {
            drawable.draw(g);
        }
    }
}
