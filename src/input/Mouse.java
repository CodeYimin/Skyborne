package input;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import core.GraphicsPanel;
import structures.Vector;

public class Mouse implements MouseListener {
    private GraphicsPanel graphicsPanel;
    private boolean pressed = false;
    private boolean onScreen = false;

    public Mouse(GraphicsPanel graphicsPanel) {
        this.graphicsPanel = graphicsPanel;
    }

    public Vector getPosition() {
        Point mousePosition = graphicsPanel.getMousePosition();
        if (mousePosition == null) {
            return null;
        }

        return new Vector(mousePosition.getX(), mousePosition.getY());
    }

    public boolean isPressed() {
        return pressed;
    }

    public boolean isOnScreen() {
        return onScreen;
    }

    public void addListener(MouseListener mouseListener) {
        graphicsPanel.addMouseListener(mouseListener);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    };

    @Override
    public void mousePressed(MouseEvent e) {
        pressed = true;
    };

    @Override
    public void mouseReleased(MouseEvent e) {
        pressed = false;
    };

    @Override
    public void mouseEntered(MouseEvent e) {
        onScreen = true;
    };

    @Override
    public void mouseExited(MouseEvent e) {
        onScreen = false;
    };
}
