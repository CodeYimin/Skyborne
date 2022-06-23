package structures;

public final class IntVector {
    public static final IntVector ZERO = new IntVector(0, 0);
    public static final IntVector ONE = new IntVector(1, 1);
    public static final IntVector UP = new IntVector(0, 1);
    public static final IntVector DOWN = new IntVector(0, -1);
    public static final IntVector LEFT = new IntVector(-1, 0);
    public static final IntVector RIGHT = new IntVector(1, 0);

    private final int x, y;

    public IntVector(double x, double y) {
        this.x = (int) x;
        this.y = (int) y;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IntVector intVector = (IntVector) o;
        return x == intVector.x && y == intVector.y;
    }
}
