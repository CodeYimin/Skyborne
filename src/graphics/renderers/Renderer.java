package graphics.renderers;

import java.awt.Graphics2D;

import entities.GameObject;
import graphics.Camera;

public interface Renderer {
    public void draw(Graphics2D g, Camera camera, GameObject objectToRender);
}
