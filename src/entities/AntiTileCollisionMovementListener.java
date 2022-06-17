package entities;

import util.Vector;

public class AntiTileCollisionMovementListener implements MovementListener {
    private Entity entity;

    public AntiTileCollisionMovementListener(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void onMove(Vector oldPosition, Vector newPosition) {
        Vector moveAmount = newPosition.subtract(oldPosition);

        // Move X
        double xMoveAmount = moveAmount.x();
        Vector xMovePosition = new Vector(newPosition.x(), oldPosition.y());
        if (entity.getCollisionManager().getIntersectingTiles(xMovePosition).size() > 0) {
            // Adjust position on collision to perfectly align with tile
            if (xMoveAmount > 0) {
                // Right collision
                entity.setRight(Math.floor(entity.getRight()));
            } else {
                // Left collision
                entity.setLeft(Math.ceil(entity.getLeft()));
            }
        }

        // Move Y
        double yMoveAmount = moveAmount.y();
        Vector yMovePosition = new Vector(oldPosition.x(), newPosition.y());
        if (entity.getCollisionManager().getIntersectingTiles(yMovePosition).size() > 0) {
            // Adjust position on collision to perfectly align with tile
            if (yMoveAmount > 0) {
                // Top collision
                entity.setTop(Math.floor(entity.getTop()));
            } else {
                // Bottom collision
                entity.setBottom(Math.ceil(entity.getBottom()));
            }
        }
    }
}
