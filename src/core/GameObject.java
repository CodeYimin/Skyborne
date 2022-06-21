package core;

import java.util.ArrayList;

import components.Component;
import components.Transform;
import events.Event;
import events.EventListener;
import events.EventManager;
import scenes.Scene;

public class GameObject {
    private GameObject parent = null;
    private Transform transform = null;
    private Scene scene = null;
    private boolean enabled = true;
    private ArrayList<Component> components = new ArrayList<>();
    private EventManager eventManager = new EventManager();

    public GameObject() {
        this.transform = new Transform();
        addComponent(this.transform);
    }

    public void start() {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).start();
        }
    }

    public void update(double deltaTime) {
        if (!enabled) {
            return;
        }

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

    public boolean isEnabled() {
        return enabled;
    }

    public void enable() {
        enabled = true;
        for (GameObject child : getChildren()) {
            child.enable();
        }
    }

    public void disable() {
        enabled = false;
        for (GameObject child : getChildren()) {
            child.disable();
        }
    }

    public Transform getTransform() {
        return transform;
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

    public boolean isDestroyed() {
        return scene == null;
    }

    public void addEventListener(EventListener<?> listener) {
        eventManager.addListener(listener);
    }

    public void removeEventListener(EventListener<?> listener) {
        eventManager.removeListener(listener);
    }

    public void emitEvent(Event event) {
        eventManager.emit(event);
    }
}
