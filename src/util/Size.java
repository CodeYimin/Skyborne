package util;

public class Size {
    private final double width;
    private final double height;

    public Size(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double width() {
        return width;
    }

    public double height() {
        return height;
    }

    public Size add(Size other) {
        return new Size(width + other.width, height + other.height);
    }

    public Size subtract(Size other) {
        return new Size(width - other.width, height - other.height);
    }

    public Size multiply(double scalar) {
        return new Size(width * scalar, height * scalar);
    }

    public Size multiply(Size other) {
        return new Size(width * other.width, height * other.height);
    }

    public Size divide(double scalar) {
        return new Size(width / scalar, height / scalar);
    }

    public double getArea() {
        return width * height;
    }

    public double getPerimeter() {
        return 2 * width + 2 * height;
    }

    public double getDiagonal() {
        return Math.sqrt(width * width + height * height);
    }
}
