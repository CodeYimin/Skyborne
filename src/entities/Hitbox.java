package entities;

import util.Size;
import util.Vector;

public class Hitbox {
    private Vector position;
    private double width;
    private double height;

    public Hitbox(Vector position, double width, double height) {
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public Hitbox(Vector position, Size size) {
        this.position = position;
        this.width = size.width();
        this.height = size.height();
    }

    public Vector position() {
        return position;
    }

    public double width() {
        return width;
    }

    public double height() {
        return height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double top() {
        return position.y() + height;
    }

    public double bottom() {
        return position.y();
    }

    public double left() {
        return position.x() - width / 2;
    }

    public double right() {
        return position.x() + width / 2;
    }

    public boolean intersects(Hitbox other) {
        return right() > other.left()
                && left() < other.right()
                && top() > other.bottom()
                && bottom() < other.top();
    }
}
