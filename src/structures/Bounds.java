package structures;

import components.Transform;

public class Bounds {
    public double x, y, width, height;

    public Bounds(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Bounds(Vector position, Vector size) {
        this.x = position.getX();
        this.y = position.getY();
        this.width = size.getX();
        this.height = size.getY();
    }

    public Bounds(Transform transform) {
        this.x = transform.getPosition().getX();
        this.y = transform.getPosition().getY();
        this.width = transform.getScale().getX();
        this.height = transform.getScale().getY();
    }

    public boolean collides(Bounds other) {
        return getRight() >= other.getLeft()
                && getLeft() <= other.getRight()
                && getTop() >= other.getBottom()
                && getBottom() <= other.getTop();
    }

    public double getLeft() {
        return x - width / 2;
    }

    public double getRight() {
        return x + width / 2;
    }

    public double getTop() {
        return y + height / 2;
    }

    public double getBottom() {
        return y - height / 2;
    }

    public Vector getPosition() {
        return new Vector(x, y);
    }

    public Vector getSize() {
        return new Vector(width, height);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
