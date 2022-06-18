package core;

import java.util.ArrayList;
import java.util.HashMap;

import components.Component;
import entities.Entity;

public class ComponentGroup<T extends Component> {
    private HashMap<Entity, T> components = new HashMap<>();
    private Class<T> componentClass;

    public ComponentGroup(Class<T> componentClass) {
        this.componentClass = componentClass;
    }

    public boolean addComponentOfEntity(Entity entity) {
        T component = entity.getComponent(componentClass);
        if (component == null || components.containsKey(entity)) {
            return false;
        }

        components.put(entity, component);
        return true;
    }

    public boolean removeComponentOfEntity(Entity entity) {
        return components.remove(entity) != null;
    }

    public ArrayList<T> getComponents() {
        return new ArrayList<>(components.values());
    }
}
