package util;

public class Vector {
    public static final Vector ZERO = new Vector(0, 0);
    public static final Vector ONE = new Vector(1, 1);
    public static final Vector LEFT = new Vector(-1, 0);
    public static final Vector RIGHT = new Vector(1, 0);
    public static final Vector UP = new Vector(0, 1);
    public static final Vector DOWN = new Vector(0, -1);

    private final double x;
    private final double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(Size size) {
        this.x = size.getWidth();
        this.y = size.getHeight();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Vector withX(double newX) {
        return new Vector(newX, y);
    }

    public Vector withY(double newY) {
        return new Vector(x, newY);
    }

    public Vector add(Vector other) {
        return new Vector(x + other.x, y + other.y);
    }

    public Vector subtract(Vector other) {
        return new Vector(x - other.x, y - other.y);
    }

    public Vector multiply(double scalar) {
        return new Vector(x * scalar, y * scalar);
    }

    public Vector multiply(Vector other) {
        return new Vector(x * other.x, y * other.y);
    }

    public Vector divide(double scalar) {
        return new Vector(x / scalar, y / scalar);
    }

    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector normalize() {
        double length = getLength();

        if (length == 0) {
            return new Vector(0, 0);
        }

        return new Vector(x / length, y / length);
    }

    public double dot(Vector other) {
        return x * other.x + y * other.y;
    }

    public double cross(Vector other) {
        return x * other.y - y * other.x;
    }

    public Vector rotate(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        return new Vector(x * cos - y * sin, x * sin + y * cos);
    }

    public Vector rotate90() {
        return new Vector(-y, x);
    }

    public Vector rotate180() {
        return new Vector(-x, -y);
    }

    public Vector rotate270() {
        return new Vector(y, -x);
    }

    public Vector reflect(Vector normal) {
        return subtract(normal.multiply(2 * dot(normal)));
    }

    public Vector reflect90() {
        return new Vector(-y, x);
    }

    public Vector reflect180() {
        return new Vector(-x, -y);
    }

    public Vector reflect270() {
        return new Vector(y, -x);
    }

    public Vector reflect(Vector normal, double angle) {
        return reflect(normal.rotate(angle));
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
