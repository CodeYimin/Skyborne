package components;

import java.util.ArrayList;

import structures.Vector;

public class TilemapMotion extends Motion {
    @Override
    public void update(double deltaTime) {
        ArrayList<Tilemap> tilemaps = getGameObject().getScene().getComponents(Tilemap.class);
        Transform transform = getGameObject().getComponent(Transform.class);

        if (tilemaps == null || transform == null) {
            return;
        }

        Vector moveAmount = getVelocity().multiply(deltaTime);

        // Move X
        double xMoveAmount = moveAmount.getX();
        Vector xMovePosition = transform.getPosition().addX(xMoveAmount);
        Hitbox xMoveHitbox = new Hitbox(xMovePosition, transform.getSize());
        if (hitboxIntesectsTilemaps(xMoveHitbox, tilemaps)) {
            // Adjust position on collision to perfectly align with tile
            if (xMoveAmount > 0) {
                // Right collision
                transform.setRight(Math.ceil(transform.getRight()));
            } else {
                // Left collision
                transform.setLeft(Math.floor(transform.getLeft()));
            }
        } else {
            transform.setPosition(xMovePosition);
        }

        // Move Y
        double yMoveAmount = moveAmount.getY();
        Vector yMovePosition = transform.getPosition().addY(yMoveAmount);
        Hitbox yMoveHitbox = new Hitbox(yMovePosition, transform.getSize());
        if (hitboxIntesectsTilemaps(yMoveHitbox, tilemaps)) {
            // Adjust position on collision to perfectly align with tile
            if (yMoveAmount > 0) {
                // Top collision
                transform.setTop(Math.ceil(transform.getTop()));
            } else {
                // Bottom collision
                transform.setBottom(Math.floor(transform.getBottom()));
            }
        } else {
            transform.setPosition(yMovePosition);
        }
    }

    private boolean hitboxIntesectsTilemaps(Hitbox hitbox, ArrayList<Tilemap> tilemaps) {
        for (Tilemap tilemap : tilemaps) {
            if (tilemap.getIntersectingTiles(hitbox).size() > 0) {
                return true;
            }
        }
        return false;
    }
}
