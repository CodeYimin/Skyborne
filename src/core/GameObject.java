package core;

import java.util.ArrayList;

import components.Component;
import scenes.Scene;

public class GameObject {
    private GameObject parent = null;
    private Scene scene = null;
    private ArrayList<Component> components = new ArrayList<>();

    public void start() {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).start();
        }
    }

    public void update(double deltaTime) {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).update(deltaTime);
        }
    }

    public void stop() {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).stop();
        }
    }

    public void addComponent(Component component) {
        component.setGameObject(this);
        this.components.add(component);
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component component : components) {
            if (componentClass.isInstance(component)) {
                return componentClass.cast(component);
            }
        }
        return null;
    }

    public <T extends Component> ArrayList<T> getComponents(Class<T> componentClass) {
        ArrayList<T> components = new ArrayList<>();
        for (Component component : this.components) {
            if (componentClass.isInstance(component)) {
                components.add(componentClass.cast(component));
            }
        }
        return components;
    }

    public <T extends Component> boolean removeComponent(Class<T> componentClass) {
        for (int i = 0; i < components.size(); i++) {
            Component component = components.get(i);
            if (componentClass.isInstance(component)) {
                component.setGameObject(null);
                components.remove(component);
                return true;
            }
        }
        return false;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public GameObject getParent() {
        return parent;
    }

    public void setParent(GameObject parent) {
        this.parent = parent;
    }

    public ArrayList<GameObject> getChildren() {
        ArrayList<GameObject> children = new ArrayList<>();
        for (GameObject child : scene.getGameObjects()) {
            if (child.getParent() == this) {
                children.add(child);
            }
        }
        return children;
    }

    public void addChild(GameObject child) {
        child.setParent(this);
    }

    public boolean removeChild(GameObject child) {
        if (child.getParent() == this) {
            child.setParent(null);
            return true;
        }
        return false;
    }

    public void destroy() {
        scene.removeGameObject(this);
    }
}
