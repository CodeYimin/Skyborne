package collision;

import entities.GameObject;
import util.Vector;

public class TileMapCollider extends Collider {
    public TileMapCollider(GameObject owner, int[][] tiles) {
        super(owner);
    }

    @Override
    public boolean collidesWith(Collider other) {
        Vector otherPos = other.getOwner().getPosition();
        double otherX = otherPos.getX();
        double otherY = otherPos.getY();

        // if (other instanceof RectangleCollider) {
        // RectangleCollider otherRectangle = (RectangleCollider) other;

        // IntVector topLeft = new IntVector((int) otherX, (int) otherY);
        // IntVector topRight = new IntVector((int) otherX + (int)
        // otherRectangle.getWidth(), (int) otherY);
        // IntVector bottomLeft = new IntVector((int) otherX, (int) otherY + (int)
        // otherRectangle.getHeight());
        // IntVector bottomRight = new IntVector((int) (otherX +
        // otherRectangle.getWidth()), (int) (otherY + otherRectangle.getHeight()));

        // return
        // }

        return false;
    }
}
