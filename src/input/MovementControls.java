package input;

public class MovementControls {
    private Keyboard keyboard;

    private int moveUp;
    private int moveDown;
    private int moveLeft;
    private int moveRight;

    public MovementControls(Keyboard keyboard, int moveUp, int moveDown, int moveLeft, int moveRight) {
        this.keyboard = keyboard;
        this.moveUp = moveUp;
        this.moveDown = moveDown;
        this.moveLeft = moveLeft;
        this.moveRight = moveRight;
    }

    public boolean isMoveUp() {
        return keyboard.isKeyDown(moveUp);
    }

    public boolean isMoveDown() {
        return keyboard.isKeyDown(moveDown);
    }

    public boolean isMoveLeft() {
        return keyboard.isKeyDown(moveLeft);
    }

    public boolean isMoveRight() {
        return keyboard.isKeyDown(moveRight);
    }
}
