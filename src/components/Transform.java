package components;

import structures.Vector;

public class Transform extends Component {
    public Vector position;
    public Vector size;

    public Transform() {
        this.position = Vector.ZERO;
        this.size = Vector.ONE;
    }

    public Transform(Vector position, Vector size) {
        this.position = position;
        this.size = size;
    }

    public Vector getScreenPosition() {
        Camera camera = getGameObject().getScene().getComponent(Camera.class);
        return camera.worldToScreenPosition(position);
    }

    public Vector getScreenSize() {
        Camera camera = getGameObject().getScene().getComponent(Camera.class);
        return camera.worldToScreenSize(size);
    }

    public Vector getPosition() {
        return position;
    }

    public Vector getSize() {
        return size;
    }

    public double getWidth() {
        return size.getX();
    }

    public double getHeight() {
        return size.getY();
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public void setSize(Vector size) {
        this.size = size;
    }

    public void setWidth(double width) {
        size = size.withX(width);
    }

    public void setHeight(double height) {
        size = size.withY(height);
    }

    public double getX() {
        return position.getX();
    }

    public double getY() {
        return position.getY();
    }

    public void setX(double x) {
        position = position.withX(x);
    }

    public void setY(double y) {
        position = position.withY(y);
    }

    public double getTop() {
        return position.getY() + size.getY() / 2;
    }

    public double getBottom() {
        return position.getY() - size.getY() / 2;
    }

    public double getLeft() {
        return position.getX() - size.getX() / 2;
    }

    public double getRight() {
        return position.getX() + size.getX() / 2;
    }

    public void setTop(double top) {
        position = position.withY(top - size.getY() / 2);
    }

    public void setBottom(double bottom) {
        position = position.withY(bottom + size.getY() / 2);
    }

    public void setLeft(double left) {
        position = position.withX(left + size.getX() / 2);
    }

    public void setRight(double right) {
        position = position.withX(right - size.getX() / 2);
    }
}
