package input;

import java.util.ArrayList;

public abstract class CompositeBinding<T> {
    private ArrayList<Binding<T>> bindings = new ArrayList<>();
    private T defaultValue;

    public CompositeBinding(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void addBinding(int keyCode, T pressedValue) {
        bindings.add(new Binding<T>(keyCode, defaultValue, pressedValue));
    }

    public ArrayList<T> getAllBindingValues(InputManager inputManager) {
        ArrayList<T> values = new ArrayList<>();
        for (Binding<T> binding : bindings) {
            values.add(binding.getValue(inputManager));
        }
        return values;
    }

    public abstract T getValue(InputManager inputManager);
}
