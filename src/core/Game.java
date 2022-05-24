package core;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import entities.Player;
import graphics.GraphicsPanel;
import input.VectorCompositeBinding;
import util.Vector;

public class Game {
    JFrame window;
    GraphicsPanel canvas;

    public Game() {
        window = new JFrame("Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);
        window.setFocusable(true);

        canvas = new GraphicsPanel();

        window.add(canvas);

        window.setVisible(true);

        VectorCompositeBinding movementControls = new VectorCompositeBinding(new Vector(0, 0));
        movementControls.addBinding(KeyEvent.VK_W, new Vector(0, 1));
        movementControls.addBinding(KeyEvent.VK_S, new Vector(0, -1));
        movementControls.addBinding(KeyEvent.VK_A, new Vector(-1, 0));
        movementControls.addBinding(KeyEvent.VK_D, new Vector(1, 0));

        Player player = new Player(movementControls);
        canvas.addDrawable(player);

        while (true) {
            canvas.repaint();
            try {
                Thread.sleep(1000 / 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
