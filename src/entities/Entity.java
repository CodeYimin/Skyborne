package entities;

import java.util.ArrayList;

import components.Component;

public class Entity {
    private ArrayList<Component> components = new ArrayList<>();

    public boolean addComponent(Component component) {
        if (getComponent(component.getClass()) != null) {
            return false;
        }

        components.add(component);
        return true;
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component component : components) {
            if (component.getClass() == componentClass) {
                return (T) component;
            }
        }
        return null;
    }
}
