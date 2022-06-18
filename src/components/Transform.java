package components;

public class Transform {
    public Vector position;
    public Vector size;

    public Transform(Vector position, Vector size) {
        this.position = position;
        this.size = size;
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
        this.size.setX(width);
    }

    public void setHeight(double height) {
        this.size.setY(height);
    }

    public double getX() {
        return position.getX();
    }

    public double getY() {
        return position.getY();
    }

    public void setX(double x) {
        this.position.setX(x);
    }

    public void setY(double y) {
        this.position.setY(y);
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
        this.position.setY(top - size.getY() / 2);
    }

    public void setBottom(double bottom) {
        this.position.setY(bottom + size.getY() / 2);
    }

    public void setLeft(double left) {
        this.position.setX(left + size.getX() / 2);
    }

    public void setRight(double right) {
        this.position.setX(right - size.getX() / 2);
    }
}
