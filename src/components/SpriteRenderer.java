package components;

import java.awt.Graphics;

import structures.Sprite;

public class SpriteRenderer extends Renderer {
    private Sprite sprite;

    public SpriteRenderer(Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public void render(Graphics g, Camera camera) {
        Transform transform = getGameObject().getComponent(Transform.class);
        if (transform == null) {
            return;
        }

        sprite.draw(g, transform.getScreenPosition(), transform.getScreenSize(), true);
    }
}
