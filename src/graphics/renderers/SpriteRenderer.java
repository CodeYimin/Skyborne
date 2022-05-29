package graphics.renderers;

import java.awt.Graphics;

import graphics.Sprite;
import util.Size;
import util.Vector;

public class SpriteRenderer implements Renderer {
    private Sprite sprite;
    private Size size;

    public SpriteRenderer(String spritePath, Size size) {
        this.sprite = new Sprite(spritePath);
        this.size = size;
    }

    public void render(Graphics g, RenderInfo renderInfo) {
        Size spriteScreenSize = size.multiply(renderInfo.zoom);
        Vector spriteScreenPos = renderInfo.objectScreenPos.subtract(new Vector(spriteScreenSize).divide(2));

        g.drawImage(sprite.getImage(),
                (int) spriteScreenPos.getX(),
                (int) spriteScreenPos.getY(),
                (int) spriteScreenSize.getWidth(),
                (int) spriteScreenSize.getHeight(),
                null);
    }
}
