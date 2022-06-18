package components;

import java.awt.Graphics;

public abstract class Renderer extends Component {
    public abstract void render(Graphics g, Camera camera);
}
