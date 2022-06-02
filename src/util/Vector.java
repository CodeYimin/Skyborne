package util;

public final class Vector {
    public static final Vector ZERO = new Vector(0, 0);
    public static final Vector ONE = new Vector(1, 1);
    public static final Vector UP = new Vector(0, 1);
    public static final Vector DOWN = new Vector(0, -1);
    public static final Vector LEFT = new Vector(-1, 0);
    public static final Vector RIGHT = new Vector(1, 0);

    private final double x;
    private final double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(Size size) {
        this.x = size.width();
        this.y = size.height();
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector normalized() {
        if (length() == 0) {
            return new Vector(0, 0);
        }

        return new Vector(x / length(), y / length());
    }

    public Vector withX(double x) {
        return new Vector(x, y);
    }

    public Vector withY(double y) {
        return new Vector(x, y);
    }

    public Vector addX(double x) {
        return new Vector(this.x + x, y);
    }

    public Vector addY(double y) {
        return new Vector(x, this.y + y);
    }

    public Vector add(double x, double y) {
        return new Vector(this.x + x, this.y + y);
    }

    public Vector add(double scalar) {
        return new Vector(this.x + scalar, this.y + scalar);
    }

    public Vector add(Vector other) {
        return new Vector(this.x + other.x(), this.y + other.y());
    }

    public Vector subtractX(double x) {
        return new Vector(this.x - x, y);
    }

    public Vector subtractY(double y) {
        return new Vector(x, this.y - y);
    }

    public Vector subtract(double x, double y) {
        return new Vector(this.x - x, this.y - y);
    }

    public Vector subtract(double scalar) {
        return new Vector(this.x - scalar, this.y - scalar);
    }

    public Vector subtract(Vector other) {
        return new Vector(this.x - other.x(), this.y - other.y());
    }

    public Vector multiplyX(double x) {
        return new Vector(this.x * x, y);
    }

    public Vector multiplyY(double y) {
        return new Vector(x, this.y * y);
    }

    public Vector multiply(double scalar) {
        return new Vector(this.x * scalar, this.y * scalar);
    }

    public Vector multiply(Vector other) {
        return new Vector(this.x * other.x(), this.y * other.y());
    }

    public Vector divideX(double x) {
        return new Vector(this.x / x, y);
    }

    public Vector divideY(double y) {
        return new Vector(x, this.y / y);
    }

    public Vector divide(double scalar) {
        return new Vector(this.x / scalar, this.y / scalar);
    }

    public Vector divide(Vector other) {
        return new Vector(this.x / other.x(), this.y / other.y());
    }

    public Vector floorX() {
        return new Vector(Math.floor(x), y);
    }

    public Vector floorY() {
        return new Vector(x, Math.floor(y));
    }

    public Vector ceilX() {
        return new Vector(Math.ceil(x), y);
    }

    public Vector ceilY() {
        return new Vector(x, Math.ceil(y));
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public Vector clone() {
        return new Vector(x, y);
    }
}
