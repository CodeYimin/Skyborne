package input;

import java.util.ArrayList;

public class IntegerCompositeBinding extends CompositeBinding<Integer> {
    public IntegerCompositeBinding(Integer defaultValue) {
        super(defaultValue);
    }

    @Override
    public Integer getValue(InputManager inputManager) {
        ArrayList<Integer> values = getAllBindingValues(inputManager);
        int value = 0;
        for (Integer i : values) {
            value += i;
        }
        return value;
    }
}
