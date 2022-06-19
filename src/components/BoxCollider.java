package components;

import java.util.ArrayList;

import core.GameObject;
import structures.Tile;
import structures.Vector;

public class BoxCollider extends Component {

    public ArrayList<Tile> getIntersectingTiles() {
        ArrayList<Tilemap> tilemaps = getGameObject().getScene().getComponents(Tilemap.class);
        ArrayList<Tile> intersectingTiles = new ArrayList<Tile>();

        for (Tilemap tilemap : tilemaps) {
            intersectingTiles.addAll(tilemap.getIntersectingTiles(this));
        }

        return intersectingTiles;
    }

    public boolean isIntersectingTiles() {
        return !getIntersectingTiles().isEmpty();
    }

    public ArrayList<Tile> getCollidingTiles() {
        ArrayList<Tilemap> tilemaps = getGameObject().getScene().getComponents(Tilemap.class);
        ArrayList<Tile> collidingTiles = new ArrayList<Tile>();

        for (Tilemap tilemap : tilemaps) {
            collidingTiles.addAll(tilemap.getCollidingTiles(this));
        }

        return collidingTiles;
    }

    public boolean isCollidingTiles() {
        return !getCollidingTiles().isEmpty();
    }

    public ArrayList<BoxCollider> getCollidingBoxColliders() {
        ArrayList<BoxCollider> collidingBoxColliders = new ArrayList<BoxCollider>();

        for (BoxCollider boxCollider : getGameObject().getScene().getComponents(BoxCollider.class)) {
            if (boxCollider != this && boxCollider.collides(this)) {
                collidingBoxColliders.add(boxCollider);
            }
        }

        return collidingBoxColliders;
    }

    public ArrayList<GameObject> getCollidingObjects() {
        ArrayList<GameObject> collidingGameObjects = new ArrayList<GameObject>();

        for (BoxCollider boxCollider : getCollidingBoxColliders()) {
            collidingGameObjects.add(boxCollider.getGameObject());
        }

        return collidingGameObjects;
    }

    public boolean isCollidingBoxColliders() {
        return !getCollidingBoxColliders().isEmpty();
    }

    public boolean collides(BoxCollider other) {
        return getRight() >= other.getLeft()
                && getLeft() <= other.getRight()
                && getTop() >= other.getBottom()
                && getBottom() <= other.getTop();
    }

    public double getLeft() {
        Transform transform = getGameObject().getComponent(Transform.class);
        return transform.getLeft();
    }

    public double getRight() {
        Transform transform = getGameObject().getComponent(Transform.class);
        return transform.getRight();
    }

    public double getTop() {
        Transform transform = getGameObject().getComponent(Transform.class);
        return transform.getTop();
    }

    public double getBottom() {
        Transform transform = getGameObject().getComponent(Transform.class);
        return transform.getBottom();
    }

    public Vector getPosition() {
        Transform transform = getGameObject().getComponent(Transform.class);
        return transform.getPosition();
    }

    public Vector getScale() {
        Transform transform = getGameObject().getComponent(Transform.class);
        return transform.getScale();
    }
}
