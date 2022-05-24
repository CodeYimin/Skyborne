package util;

public class Vector {
    private final double x;
    private final double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
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

    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector normalize() {
        double length = getLength();
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
}
