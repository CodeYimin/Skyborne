package scenes;

import java.util.ArrayList;

import components.Component;
import core.Game;
import core.GameObject;

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

        int size = gameObjects.size();
        for (int i = 0; i < size; i++) {
            gameObjects.get(i).start();
        }
    }

    public void update(double deltaTime) {
        for (int i = 0; i < gameObjects.size(); i++) {
            gameObjects.get(i).update(deltaTime);
        }
    };

    public void stop() {
        for (GameObject gameObject : gameObjects) {
            gameObject.stop();
        }
        running = false;
    }

    public Game getGame() {
        return game;
    }

    public boolean isRunning() {
        return running;
    }

    public ArrayList<GameObject> getGameObjects() {
        ArrayList<GameObject> gameObjects = new ArrayList<>();
        for (GameObject gameObject : this.gameObjects) {
            gameObjects.add(gameObject);
        }
        return gameObjects;
    }

    public ArrayList<GameObject> getGameObjects(Class<? extends Component> componentClass) {
        ArrayList<GameObject> gameObjects = new ArrayList<>();

        for (GameObject gameObject : this.gameObjects) {
            if (gameObject.getComponent(componentClass) != null) {
                gameObjects.add(gameObject);
            }
        }

        return gameObjects;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<GameObject> getGameObjects(Class<? extends Component>... componentClasses) {
        ArrayList<GameObject> gameObjects = new ArrayList<>();

        for (GameObject gameObject : this.gameObjects) {
            boolean hasAllComponents = true;
            for (Class<? extends Component> componentClass : componentClasses) {
                if (gameObject.getComponent(componentClass) == null) {
                    hasAllComponents = false;
                }
            }
            if (hasAllComponents) {
                gameObjects.add(gameObject);
            }
        }

        return gameObjects;
    }

    @SuppressWarnings("unchecked")
    public GameObject getGameObject(Class<? extends Component>... componentClasses) {
        for (GameObject gameObject : this.gameObjects) {
            boolean hasAllComponents = true;
            for (Class<? extends Component> componentClass : componentClasses) {
                if (gameObject.getComponent(componentClass) == null) {
                    hasAllComponents = false;
                }
            }
            if (hasAllComponents) {
                return gameObject;
            }
        }
        return null;
    }

    public GameObject getGameObject(Class<? extends Component> componentClass) {
        for (GameObject gameObject : this.gameObjects) {
            if (gameObject.getComponent(componentClass) != null) {
                return gameObject;
            }
        }
        return null;
    }

    public boolean removeGameObject(GameObject gameObject) {
        gameObject.stop();
        for (GameObject child : gameObject.getChildren()) {
            removeGameObject(child);
        }
        return gameObjects.remove(gameObject);
    }

    public <T extends Component> ArrayList<T> getComponents(Class<T> componentClass) {
        ArrayList<T> components = new ArrayList<>();
        for (int i = 0; i < gameObjects.size(); i++) {
            ArrayList<T> gameObjectComponents = gameObjects.get(i).getComponents(componentClass);
            if (gameObjectComponents != null) {
                components.addAll(gameObjectComponents);
            }
        }
        return components;
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (int i = 0; i < gameObjects.size(); i++) {
            T component = gameObjects.get(i).getComponent(componentClass);
            if (component != null) {
                return component;
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
