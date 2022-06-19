package structures;

public class Directions {
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;

    public Directions() {
        this.up = false;
        this.down = false;
        this.left = false;
        this.right = false;
    }

    public Directions(boolean up, boolean down, boolean left, boolean right) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    public boolean hasUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean hasDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean hasLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean hasRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }
}
