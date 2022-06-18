package input;

public class PlayerControls {
    private int moveUp;
    private int moveDown;
    private int moveLeft;
    private int moveRight;

    private final int attack;
    // public final int interact;

    public PlayerControls(int moveUp, int moveDown, int moveLeft, int moveRight, int attack) {
        this.moveUp = moveUp;
        this.moveDown = moveDown;
        this.moveLeft = moveLeft;
        this.moveRight = moveRight;
        this.attack = attack;
        // this.interact = interact;
    }

    public int getMoveUp() {
        return moveUp;
    }

    public int getMoveDown() {
        return moveDown;
    }

    public int getMoveLeft() {
        return moveLeft;
    }

    public int getMoveRight() {
        return moveRight;
    }
}
