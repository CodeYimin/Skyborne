package components;

import java.awt.Graphics;

import structures.Sprite;

public class SpriteRenderer extends Renderer {
    private Sprite sprite;
    private boolean flipX;
    private boolean flipY;

    public SpriteRenderer(Sprite sprite) {
        this.sprite = sprite;
    }

    public SpriteRenderer(String spritePath) {
        this.sprite = new Sprite(spritePath);
    }

    public boolean isFlipX() {
        return flipX;
    }

    public void setFlipX(boolean flipX) {
        this.flipX = flipX;
    }

    public boolean isFlipY() {
        return flipY;
    }

    public void setFlipY(boolean flipY) {
        this.flipY = flipY;
    }

    @Override
    public void render(Graphics g, Camera camera) {
        Transform transform = getGameObject().getComponent(Transform.class);
        if (transform == null) {
            return;
        }

        double coterminalAngle = transform.getRotation() % (Math.PI * 2);
        while (coterminalAngle < 0) {
            coterminalAngle += Math.PI * 2;
        }

        sprite.draw(g,
                transform.getScreenPosition(),
                transform.getScreenScale(),
                transform.getRotation(),
                flipX, flipY);
    }
}
