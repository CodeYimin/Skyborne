package entities;

import components.Vector;

public class AntiTileCollisionMovementListener implements MovementListener {
    private Entity entity;

    public AntiTileCollisionMovementListener(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void onMove(Vector oldPosition, Vector newPosition) {
        Vector moveAmount = newPosition.subtract(oldPosition);

        // Move X
        double xMoveAmount = moveAmount.getX();
        Vector xMovePosition = new Vector(newPosition.getX(), oldPosition.getY());
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
        double yMoveAmount = moveAmount.getY();
        Vector yMovePosition = new Vector(oldPosition.getX(), newPosition.getY());
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
