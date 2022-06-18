package graphics;

import java.awt.Graphics;

import components.Vector;
import util.Size;

public class AnimatedSprite {
    public static final int IDLE = 0;
    public static final int RUNNING = 1;

    private int frameTime;
    private Sprite[] idleFrames;
    private Sprite[] runningFrames;
    private int state = IDLE;

    public AnimatedSprite(int frameTime, Sprite[] idleFrames, Sprite[] runningFrames) {
        this.frameTime = frameTime;
        this.idleFrames = idleFrames;
        this.runningFrames = runningFrames;
    }

    public void draw(Graphics g, Vector screenPosition, Size size, boolean flip) {
        Sprite sprite = getCurrentSprite();
        sprite.draw(g, screenPosition, size, flip);
    }

    public Sprite[] getCurrentFrames() {
        if (state == IDLE) {
            return idleFrames;
        } else if (state == RUNNING) {
            return runningFrames;
        } else {
            return null;
        }
    }

    public Sprite getCurrentSprite() {
        Sprite[] frames = getCurrentFrames();
        int frameIndex = (int) (System.currentTimeMillis() / frameTime % frames.length);
        return frames[frameIndex];
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
