package input;

import java.util.ArrayList;

public class IntegerCompositeBinding extends CompositeBinding<Integer> {
    public IntegerCompositeBinding(InputManager inputManager, Integer defaultValue) {
        super(inputManager, defaultValue);
    }

    @Override
    public Integer getValue() {
        ArrayList<Integer> values = getAllBindingValues();
        int value = 0;
        for (Integer i : values) {
            value += i;
        }
        return value;
    }
}
