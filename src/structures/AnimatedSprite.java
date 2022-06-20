package structures;

import java.awt.Graphics;

public class AnimatedSprite {
    private int frameTime;
    private Sprite[] frames;

    public AnimatedSprite(int frameTime, Sprite[] frames) {
        this.frameTime = frameTime;
        this.frames = frames;
    }

    public void draw(Graphics g, Vector screenPosition, Vector size, double angle, boolean flipX, boolean flipY) {
        Sprite sprite = getCurrentSprite();
        sprite.draw(g, screenPosition, size, angle, flipX, flipY);
    }

    public Sprite[] getFrames() {
        return frames;
    }

    public Sprite getCurrentSprite() {
        int frameIndex = (int) (System.currentTimeMillis() / frameTime % frames.length);
        return frames[frameIndex];
    }
}
