package input;

public final class PlayerControls {
    public final int up;
    public final int down;
    public final int left;
    public final int right;

    public final int attack;
    // public final int interact;

    public PlayerControls(int up, int down, int left, int right, int attack) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.attack = attack;
        // this.interact = interact;
    }
}
