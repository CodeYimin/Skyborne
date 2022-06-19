package components;

import structures.Vector;

public class TilemapMotion extends Motion {
    @Override
    public void update(double deltaTime) {
        Transform transform = getGameObject().getComponent(Transform.class);
        BoxCollider boxCollider = getGameObject().getComponent(BoxCollider.class);

        if (boxCollider == null || transform == null) {
            return;
        }

        Vector moveXY = getVelocity().multiply(deltaTime);
        double moveX = moveXY.getX();
        double moveY = moveXY.getY();

        // Move X
        transform.setPosition(transform.getPosition().addX(moveX));
        if (boxCollider.isIntersectingTiles()) {
            // Adjust position on collision to perfectly align with tile
            if (moveX > 0) {
                // Right collision
                transform.setRight(Math.floor(transform.getRight()));
            } else {
                // Left collision
                transform.setLeft(Math.ceil(transform.getLeft()));
            }
        }

        // Move Y
        transform.setPosition(transform.getPosition().addY(moveY));
        if (boxCollider.isIntersectingTiles()) {
            // Adjust position on collision to perfectly align with tile
            if (moveY > 0) {
                // Top collision
                transform.setTop(Math.floor(transform.getTop()));
            } else {
                // Bottom collision
                transform.setBottom(Math.ceil(transform.getBottom()));
            }
        }
    }
}
