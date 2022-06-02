package core;

import input.InputManager;
import scenes.World;
import util.Const;

public class Game {
    private GameWindow window = new GameWindow("Epic Game");
    private InputManager inputManager = new InputManager();

    private World level = new World(this);

    public Game() {
        window.addKeyListener(inputManager);

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

    public InputManager getInputManager() {
        return inputManager;
    }
}
