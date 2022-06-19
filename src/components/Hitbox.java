package components;

import structures.Vector;

public class Hitbox extends Component {
    private Vector position;
    private double width;
    private double height;

    public Hitbox(Vector position, double width, double height) {
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public Hitbox(Vector position, Vector size) {
        this.position = position;
        this.width = size.getX();
        this.height = size.getY();
    }

    public Hitbox(Transform transform) {
        this.position = transform.getPosition();
        this.width = transform.getScale().getX();
        this.height = transform.getScale().getY();
    }

    public Vector position() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
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
        return position.getY() + height / 2;
    }

    public double bottom() {
        return position.getY() - height / 2;
    }

    public double left() {
        return position.getX() - width / 2;
    }

    public double right() {
        return position.getX() + width / 2;
    }

    public boolean intersects(Hitbox other) {
        return right() > other.left()
                && left() < other.right()
                && top() > other.bottom()
                && bottom() < other.top();
    }
}
