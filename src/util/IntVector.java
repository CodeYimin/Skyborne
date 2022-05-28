package util;

public class IntVector {
    private final int x;
    private final int y;

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

    public IntVector subtract(IntVector other) {
        return new IntVector(x - other.x, y - other.y);
    }

    public IntVector multiply(int scalar) {
        return new IntVector(x * scalar, y * scalar);
    }

    public IntVector multiply(IntVector other) {
        return new IntVector(x * other.x, y * other.y);
    }

    public IntVector divide(int scalar) {
        return new IntVector(x / scalar, y / scalar);
    }
}
