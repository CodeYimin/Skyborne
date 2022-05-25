package graphics.renderers;

import java.awt.Graphics2D;

import graphics.RenderInfo;

public interface Renderer {
    public void render(Graphics2D g, RenderInfo renderInfo);
}
