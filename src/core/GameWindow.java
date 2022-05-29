package core;

import javax.swing.JFrame;

public class GameWindow extends JFrame {
    private GraphicsPanel graphicsPanel;

    public GameWindow(String title) {
        super(title);

        graphicsPanel = new GraphicsPanel();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setFocusable(true);
        add(graphicsPanel);
        setVisible(true);
    }

    public GraphicsPanel getGraphicsPanel() {
        return graphicsPanel;
    }
}
