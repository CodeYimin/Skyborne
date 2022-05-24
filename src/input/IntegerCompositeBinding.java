package input;

public class IntegerCompositeBinding extends CompositeBinding<Integer> {
    public IntegerCompositeBinding(InputManager inputManager, Integer defaultValue) {
        super(inputManager, defaultValue);
    }

    @Override
    public Integer combineValues(Integer value1, Integer value2) {
        return value1 + value2;
    }
}
