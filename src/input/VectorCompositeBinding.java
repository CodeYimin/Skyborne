package input;

import util.Vector;

public class VectorCompositeBinding extends CompositeBinding<Vector> {
    public VectorCompositeBinding(InputManager inputManager, Vector defaultValue) {
        super(inputManager, defaultValue);
    }

    @Override
    public Vector combineValues(Vector value1, Vector value2) {
        return value1.add(value2);
    }
}
