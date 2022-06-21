package core;

import java.awt.Graphics;

public interface Drawable {
    public int getZIndex();

    public void draw(Graphics g);
}
