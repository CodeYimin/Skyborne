package structures;

public final class Vector {
    public static final Vector ZERO = new Vector(0, 0);
    public static final Vector ONE = new Vector(1, 1);
    public static final Vector UP = new Vector(0, 1);
    public static final Vector DOWN = new Vector(0, -1);
    public static final Vector LEFT = new Vector(-1, 0);
    public static final Vector RIGHT = new Vector(1, 0);

    public static final Vector UP_DIRECTION = UP;
    public static final Vector DOWN_DIRECTION = DOWN;
    public static final Vector LEFT_DIRECTION = LEFT;
    public static final Vector RIGHT_DIRECTION = RIGHT;
    public static final Vector UP_RIGHT_DIRECTION = new Vector(1, 1).normalized();
    public static final Vector UP_LEFT_DIRECTION = new Vector(-1, 1).normalized();
    public static final Vector DOWN_RIGHT_DIRECTION = new Vector(1, -1).normalized();
    public static final Vector DOWN_LEFT_DIRECTION = new Vector(-1, -1).normalized();

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
        return new Vector(this.x + other.getX(), this.y + other.getY());
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
        return new Vector(this.x - other.getX(), this.y - other.getY());
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
        return new Vector(this.x * other.getX(), this.y * other.getY());
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
        return new Vector(this.x / other.getX(), this.y / other.getY());
    }

    public Vector floorgetX() {
        return new Vector(Math.floor(x), y);
    }

    public Vector floorgetY() {
        return new Vector(x, Math.floor(y));
    }

    public Vector floor() {
        return new Vector(Math.floor(x), Math.floor(y));
    }

    public Vector ceilgetX() {
        return new Vector(Math.ceil(x), y);
    }

    public Vector ceilgetY() {
        return new Vector(x, Math.ceil(y));
    }

    public Vector ceil() {
        return new Vector(Math.ceil(x), Math.ceil(y));
    }

    public Vector roundgetX() {
        return new Vector(Math.round(x), y);
    }

    public Vector roundgetY() {
        return new Vector(x, Math.round(y));
    }

    public Vector round() {
        return new Vector(Math.round(x), Math.round(y));
    }

    public Vector absgetX() {
        return new Vector(Math.abs(x), y);
    }

    public Vector absgetY() {
        return new Vector(x, Math.abs(y));
    }

    public Vector abs() {
        return new Vector(Math.abs(x), Math.abs(y));
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public Vector clone() {
        return new Vector(x, y);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof Vector)) {
            return false;
        }

        Vector otherVector = (Vector) other;
        return x == otherVector.x && y == otherVector.y;
    }
}
