package systems;

import components.Hitbox;
import components.Transform;
import components.Vector;
import world.Tilemap;

public class TilemapMovementSystem extends System<TilemapMovementNode> {
    public TilemapMovementSystem(int priority) {
        super(priority);
    }

    @Override
    public void updateNode(TilemapMovementNode node, double deltaTime) {
        Tilemap tilemap = node.getTilemap();
        Vector velocity = node.getVelocity();
        Transform transform = node.getTransform();

        Vector moveAmount = velocity.multiply(deltaTime);

        // Move X
        double xMoveAmount = moveAmount.getX();
        Vector xMovePosition = transform.getPosition().clone().addX(xMoveAmount);
        Hitbox xMoveHitbox = new Hitbox(xMovePosition, transform.getSize());
        boolean xMoveIntersects = tilemap.getIntersectingTiles(xMoveHitbox).size() > 0;
        if (xMoveIntersects) {
            // Adjust position on collision to perfectly align with tile
            if (xMoveAmount > 0) {
                // Right collision
                transform.setRight(Math.ceil(transform.getRight()));
            } else {
                // Left collision
                transform.setLeft(Math.floor(transform.getLeft()));
            }
        }

        // Move Y
        double yMoveAmount = moveAmount.getY();
        Vector yMovePosition = transform.getPosition().clone().addY(yMoveAmount);
        Hitbox yMoveHitbox = new Hitbox(yMovePosition, transform.getSize());
        boolean yMoveIntersects = tilemap.getIntersectingTiles(yMoveHitbox).size() > 0;
        if (yMoveIntersects) {
            // Adjust position on collision to perfectly align with tile
            if (yMoveAmount > 0) {
                // Top collision
                transform.setTop(Math.ceil(transform.getTop()));
            } else {
                // Bottom collision
                transform.setBottom(Math.floor(transform.getBottom()));
            }
        }
    }
}
