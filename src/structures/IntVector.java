package structures;

public final class IntVector {
    private final int x, y;

    public IntVector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public IntVector add(IntVector other) {
        return new IntVector(x + other.x, y + other.y);
    }

    public IntVector add(int x, int y) {
        return new IntVector(this.x + x, this.y + y);
    }

    public IntVector add(int scalar) {
        return new IntVector(this.x + scalar, this.y + scalar);
    }

    public IntVector subtract(IntVector other) {
        return new IntVector(x - other.x, y - other.y);
    }

    public IntVector subtract(int x, int y) {
        return new IntVector(this.x - x, this.y - y);
    }

    public IntVector subtract(int scalar) {
        return new IntVector(this.x - scalar, this.y - scalar);
    }

    public Vector toVector() {
        return new Vector(x, y);
    }
}
