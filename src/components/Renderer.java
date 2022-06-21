package components;

import java.awt.Graphics;

import structures.Bounds;

public abstract class Renderer extends Component {
    public abstract Bounds getRenderBounds();

    public abstract void render(Graphics g, Camera camera);
}
