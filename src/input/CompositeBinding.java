package input;

import java.util.ArrayList;

public abstract class CompositeBinding<T> {
    private InputManager inputManager;
    private ArrayList<Binding<T>> bindings = new ArrayList<>();
    private T defaultValue;

    public CompositeBinding(InputManager inputManager, T defaultValue) {
        this.inputManager = inputManager;
        this.defaultValue = defaultValue;
    }

    public void addBinding(int keyCode, T pressedValue) {
        bindings.add(new Binding<T>(inputManager, keyCode, defaultValue, pressedValue));
    }

    public ArrayList<T> getAllBindingValues() {
        ArrayList<T> values = new ArrayList<>();
        for (Binding<T> binding : bindings) {
            values.add(binding.getValue());
        }
        return values;
    }

    public abstract T combineValues(T value1, T value2);

    public T getCombinedValue() {
        ArrayList<T> values = getAllBindingValues();
        T value = defaultValue;
        for (T v : values) {
            value = combineValues(value, v);
        }
        return value;
    };
}
