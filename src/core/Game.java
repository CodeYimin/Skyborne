package core;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JFrame;

import entities.GameObject;
import entities.Player;
import graphics.Camera;
import graphics.GraphicsPanel;
import input.InputManager;
import input.VectorCompositeBinding;
import util.Const;
import util.Vector;
import world.Map;

public class Game {
    private JFrame window = new JFrame("Skyborne");
    private GraphicsPanel graphicsPanel = new GraphicsPanel();
    private InputManager inputManager = new InputManager();

    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private Time time = new Time();

    public Game() {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);
        window.setFocusable(true);
        window.add(graphicsPanel);
        window.setVisible(true);
        window.addKeyListener(inputManager);

        VectorCompositeBinding movementControls = new VectorCompositeBinding(inputManager, new Vector(0, 0));
        movementControls.addBinding(KeyEvent.VK_W, new Vector(0, 1));
        movementControls.addBinding(KeyEvent.VK_S, new Vector(0, -1));
        movementControls.addBinding(KeyEvent.VK_A, new Vector(-1, 0));
        movementControls.addBinding(KeyEvent.VK_D, new Vector(1, 0));

        Map map = new Map(new boolean[10][10]);
        addGameObject(map);

        Player player = new Player(movementControls);
        addGameObject(player);

        Camera camera = new Camera(graphicsPanel, gameObjects, 100);
        camera.setFollowing(player);
        addGameObject(camera);

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
        UpdateInfo updateInfo = new UpdateInfo(time);

        for (GameObject gameObject : gameObjects) {
            gameObject.update(updateInfo);
        }

        graphicsPanel.repaint();
        time.update();
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }
}
