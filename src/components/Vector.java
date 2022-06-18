package components;

import util.Size;

public final class Vector {
    private double x;
    private double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(Size size) {
        this.x = size.width();
        this.y = size.height();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector vector) {
        this.x = vector.getX();
        this.y = vector.getY();
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector normalize() {
        double length = length();
        return new Vector(x / length, y / length);
    }

    public Vector normalized() {
        double length = length();

        if (length == 0) {
            return new Vector(0, 0);
        }

        return new Vector(x / length, y / length);
    }

    public Vector addX(double x) {
        this.x += x;
        return this;
    }

    public Vector addY(double y) {
        this.y += y;
        return this;
    }

    public Vector add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector add(Vector other) {
        this.x += other.getX();
        this.y += other.getY();
        return this;
    }

    public Vector add(double scalar) {
        this.x += scalar;
        this.y += scalar;
        return this;
    }

    public Vector subtractX(double x) {
        this.x -= x;
        return this;
    }

    public Vector subtractY(double y) {
        this.y -= y;
        return this;
    }

    public Vector subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Vector subtract(Vector other) {
        this.x -= other.getX();
        this.y -= other.getY();
        return this;
    }

    public Vector subtract(double scalar) {
        this.x -= scalar;
        this.y -= scalar;
        return this;
    }

    public Vector multiplyX(double x) {
        this.x *= x;
        return this;
    }

    public Vector multiplyY(double y) {
        this.y *= y;
        return this;
    }

    public Vector multiply(double x, double y) {
        this.x *= x;
        this.y *= y;
        return this;
    }

    public Vector multiply(Vector other) {
        this.x *= other.getX();
        this.y *= other.getY();
        return this;
    }

    public Vector multiply(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

    public Vector divideX(double x) {
        this.x /= x;
        return this;
    }

    public Vector divideY(double y) {
        this.y /= y;
        return this;
    }

    public Vector divide(double x, double y) {
        this.x /= x;
        this.y /= y;
        return this;
    }

    public Vector divide(Vector other) {
        this.x /= other.getX();
        this.y /= other.getY();
        return this;
    }

    public Vector divide(double scalar) {
        this.x /= scalar;
        this.y /= scalar;
        return this;
    }

    public Vector floorX() {
        this.x = Math.floor(x);
        return this;
    }

    public Vector floorY() {
        this.y = Math.floor(y);
        return this;
    }

    public Vector floor() {
        this.x = Math.floor(x);
        this.y = Math.floor(y);
        return this;
    }

    public Vector ceilX() {
        this.x = Math.ceil(x);
        return this;
    }

    public Vector ceilY() {
        this.y = Math.ceil(y);
        return this;
    }

    public Vector ceil() {
        this.x = Math.ceil(x);
        this.y = Math.ceil(y);
        return this;
    }

    public Vector roundX() {
        this.x = Math.round(x);
        return this;
    }

    public Vector roundY() {
        this.y = Math.round(y);
        return this;
    }

    public Vector round() {
        this.x = Math.round(x);
        this.y = Math.round(y);
        return this;
    }

    public Vector absX() {
        this.x = Math.abs(x);
        return this;
    }

    public Vector absY() {
        this.y = Math.abs(y);
        return this;
    }

    public Vector abs() {
        this.x = Math.abs(x);
        this.y = Math.abs(y);
        return this;
    }

    public Vector negateX() {
        this.x = -x;
        return this;
    }

    public Vector negateY() {
        this.y = -y;
        return this;
    }

    public Vector negate() {
        this.x = -x;
        this.y = -y;
        return this;
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
