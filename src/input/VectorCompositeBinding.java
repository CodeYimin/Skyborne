package input;

import java.util.ArrayList;

import util.Vector;

public class VectorCompositeBinding extends CompositeBinding<Vector> {
    public VectorCompositeBinding(InputManager inputManager, Vector defaultValue) {
        super(inputManager, defaultValue);
    }

    @Override
    public Vector getValue() {
        ArrayList<Vector> values = getAllBindingValues();
        Vector value = new Vector(0, 0);
        for (Vector v : values) {
            value = value.add(v);
        }
        return value;
    }
}
