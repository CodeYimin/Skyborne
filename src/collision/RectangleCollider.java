package collision;

import entities.GameObject;
import util.Vector;

public class RectangleCollider extends Collider {
    private double width;
    private double height;

    public RectangleCollider(GameObject owner, double width, double height) {
        super(owner);
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public boolean collidesWith(Collider other) {
        Vector myPos = getOwner().getPosition();
        double myX = myPos.getX();
        double myY = myPos.getY();

        Vector otherPos = other.getOwner().getPosition();
        double otherX = otherPos.getX();
        double otherY = otherPos.getY();

        if (other instanceof RectangleCollider) {
            RectangleCollider otherRectangle = (RectangleCollider) other;

            boolean xCollides = (myX + width >= otherX) && (myX <= otherX + otherRectangle.getWidth());
            boolean yCollides = (myY + height >= otherY) && (myY <= otherY + otherRectangle.getHeight());

            return xCollides && yCollides;
        }

        return false;
    }
}
