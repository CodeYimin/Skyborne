package systems;

import java.util.ArrayList;

import core.Updatable;

public abstract class System<T extends SystemNode> implements Updatable {
    private int priority;
    private ArrayList<T> nodes;

    public System(int priority) {
        this.priority = priority;
        this.nodes = new ArrayList<>();
    }

    public int getPriority() {
        return priority;
    }

    public void addNode(T node) {
        nodes.add(node);
    }

    public abstract void updateNode(T node, double deltaTime);

    @Override
    public void update(double deltaTime) {
        for (T node : nodes) {
            updateNode(node, deltaTime);
        }
    };
}
