package input;

public class Binding<T> {
    private InputManager inputManager;
    private int keyCode;
    private T defaultValue;
    private T pressedValue;

    public Binding(InputManager inputManager, int keyCode, T defaultValue, T pressedValue) {
        this.inputManager = inputManager;
        this.keyCode = keyCode;
        this.defaultValue = defaultValue;
        this.pressedValue = pressedValue;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public T getPressedValue() {
        return pressedValue;
    }

    public boolean isPressed() {
        return inputManager.getKeysHeld().contains(keyCode);
    }

    public T getValue() {
        if (isPressed()) {
            return pressedValue;
        } else {
            return defaultValue;
        }
    }
}
