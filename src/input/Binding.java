package input;

public class Binding<T> {
    private int keyCode;
    private T defaultValue;
    private T pressedValue;

    public Binding(int keyCode, T defaultValue, T pressedValue) {
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

    public boolean isPressed(InputManager inputManager) {
        return inputManager.getKeysHeld().contains(keyCode);
    }

    public T getValue(InputManager inputManager) {
        if (isPressed(inputManager)) {
            return pressedValue;
        } else {
            return defaultValue;
        }
    }
}
