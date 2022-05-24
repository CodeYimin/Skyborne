package input;

import java.util.ArrayList;

import util.Vector;

public class VectorCompositeBinding extends CompositeBinding<Vector> {
    public VectorCompositeBinding(Vector defaultValue) {
        super(defaultValue);
    }

    @Override
    public Vector getValue(InputManager inputManager) {
        ArrayList<Vector> values = getAllBindingValues(inputManager);
        Vector value = new Vector(0, 0);
        for (Vector v : values) {
            value = value.add(v);
        }
        return value;
    }
}
