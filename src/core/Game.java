package core;

import input.Keyboard;
import input.Mouse;
import scenes.LevelScene;
import scenes.Scene;
import util.Const;

public class Game {
    private GameWindow window;
    private Keyboard keyboard;
    private Mouse mouse;
    private Scene currentScene;
    private DeltaTimeTracker updateTimeTracker;

    public Game() {
        this.window = new GameWindow("Epic Game");
        this.keyboard = new Keyboard();
        this.mouse = new Mouse(window.getGraphicsPanel());
        this.updateTimeTracker = new DeltaTimeTracker();

        window.addKeyListener(keyboard);
        window.getGraphicsPanel().addMouseListener(mouse);

        init();
        start();
    }

    private void init() {
        currentScene = new LevelScene(this);
        currentScene.init();
    }

    private void start() {
        currentScene.start();
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
        currentScene.update(updateTimeTracker.getDeltaTimeSecs());
        window.getGraphicsPanel().repaint();

        updateTimeTracker.updateDeltaTime();
    }

    public GameWindow getWindow() {
        return window;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public Mouse getMouse() {
        return mouse;
    }
}
