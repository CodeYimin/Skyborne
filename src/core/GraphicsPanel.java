package core;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import util.ArrayUtils;

public class GraphicsPanel extends JPanel {
    private final ArrayList<Drawable> drawables = new ArrayList<>();

    public GraphicsPanel() {
        // Do nothing
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
        drawables.sort(new DrawableZIndexComparator());
        for (Drawable drawable : ArrayUtils.copyOf(drawables)) {
            drawable.draw((Graphics2D) g);
        }
    }
}
