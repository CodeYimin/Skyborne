package scenes;

import java.util.ArrayList;

import components.Component;
import core.Game;
import core.GameObject;
import util.ArrayUtils;

public abstract class Scene {
    private Game game;
    private boolean running;
    private ArrayList<GameObject> gameObjects;

    public Scene(Game game) {
        this.game = game;
        this.running = false;
        this.gameObjects = new ArrayList<>();
    }

    public abstract void init();

    public void start() {
        running = true;

        for (GameObject gameObject : ArrayUtils.copyOf(gameObjects)) {
            gameObject.start();
        }
    }

    public void update(double deltaTime) {
        if (!running) {
            return;
        }

        for (GameObject gameObject : ArrayUtils.copyOf(gameObjects)) {
            if (!gameObject.isDestroyed()) {
                gameObject.update(deltaTime);
            }
        }
    };

    public void stop() {
        for (GameObject gameObject : ArrayUtils.copyOf(gameObjects)) {
            gameObject.destroy();
        }
        running = false;
    }

    public void reset() {
        stop();
        init();
        start();
    }

    public Game getGame() {
        return game;
    }

    public boolean isRunning() {
        return running;
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public ArrayList<GameObject> getGameObjects(Class<? extends Component> componentClass) {
        ArrayList<GameObject> gameObjects = new ArrayList<>();
        for (GameObject gameObject : ArrayUtils.copyOf(this.gameObjects)) {
            if (gameObject.getComponent(componentClass) != null) {
                gameObjects.add(gameObject);
            }
        }
        return gameObjects;
    }

    public GameObject getGameObject(Class<? extends Component> componentClass) {
        for (GameObject gameObject : ArrayUtils.copyOf(this.gameObjects)) {
            if (gameObject.getComponent(componentClass) != null) {
                return gameObject;
            }
        }
        return null;
    }

    public boolean removeGameObject(GameObject gameObject) {
        for (GameObject child : ArrayUtils.copyOf(gameObject.getChildren())) {
            removeGameObject(child);
        }
        gameObject.setScene(null);
        return gameObjects.remove(gameObject);
    }

    public <T extends Component> ArrayList<T> getComponents(Class<T> componentClass) {
        ArrayList<T> components = new ArrayList<>();
        for (GameObject gameObject : ArrayUtils.copyOf(gameObjects)) {
            if (gameObject.getComponent(componentClass) != null) {
                for (T component : gameObject.getComponents(componentClass)) {
                    components.add(component);
                }
            }
        }
        return components;
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (GameObject gameObject : ArrayUtils.copyOf(gameObjects)) {
            if (gameObject.getComponent(componentClass) != null) {
                return componentClass.cast(gameObject.getComponent(componentClass));
            }
        }
        return null;
    }

    public void addGameObject(GameObject gameObject) {
        gameObject.setScene(this);
        this.gameObjects.add(gameObject);
        if (running) {
            gameObject.start();
        }
    }

    public void addGameObject(GameObject gameObject, int index) {
        gameObject.setScene(this);
        this.gameObjects.add(index, gameObject);
        if (running) {
            gameObject.start();
        }
    }
}
