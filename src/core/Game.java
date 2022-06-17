package core;

import input.Keyboard;
import input.Mouse;
import scenes.Level;
import util.Const;

public class Game {
    private GameWindow window = new GameWindow("Epic Game");
    private Keyboard keyboard;
    private Mouse mouse;

    private Level level;

    public Game() {
        keyboard = new Keyboard();
        mouse = new Mouse(window.getGraphicsPanel());

        window.addKeyListener(keyboard);
        window.getGraphicsPanel().addMouseListener(mouse);

        level = new Level(this);

        startGameLoop();
    }

    private void startGameLoop() {
        while (true) {
            update();
            try {
                Thread.sleep(1000 / Const.FPS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        level.update();

        window.getGraphicsPanel().repaint();
    }

    public GameWindow getWindow() {
        return window;
    }

    public Keyboard getkeyboard() {
        return keyboard;
    }

    public Mouse getMouse() {
        return mouse;
    }
}
