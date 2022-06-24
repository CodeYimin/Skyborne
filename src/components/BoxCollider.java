package components;

import java.util.ArrayList;

import core.GameObject;
import structures.Tile;
import structures.Vector;

/**
 * Gets collision information with tilemaps and other BoxColliders within the
 * scene
 * 
 * @author Yimin Sun
 */
public class BoxCollider extends Component {

    /**
     * Gets tiles that intersect the collider, not including tiles that are
     * perfectly touching the collider
     * 
     * @return ArrayList of tiles that intersect the collider
     */
    public ArrayList<Tile> getIntersectingTiles() {
        ArrayList<Tilemap> tilemaps = getGameObject().getScene().getComponents(Tilemap.class);
        ArrayList<Tile> intersectingTiles = new ArrayList<Tile>();

        for (Tilemap tilemap : tilemaps) {
            intersectingTiles.addAll(tilemap.getIntersectingTiles(this));
        }

        return intersectingTiles;
    }

    /**
     * @return if the collider is intesecting with any tiles of any tilemaps in the
     *         scene
     */
    public boolean isIntersectingTiles() {
        return !getIntersectingTiles().isEmpty();
    }

    /**
     * Gets tiles that are touching/intesecting the collider, including tiles that
     * are perfectly touching the collider
     * 
     * @return ArrayList of tiles that are touching the collider
     */
    public ArrayList<Tile> getCollidingTiles() {
        ArrayList<Tilemap> tilemaps = getGameObject().getScene().getComponents(Tilemap.class);
        ArrayList<Tile> collidingTiles = new ArrayList<Tile>();

        for (Tilemap tilemap : tilemaps) {
            collidingTiles.addAll(tilemap.getCollidingTiles(this));
        }

        return collidingTiles;
    }

    /**
     * @return if the collider is touching/intesecting any tiles of any tilemaps in
     *         the scene
     */
    public boolean isCollidingTiles() {
        return !getCollidingTiles().isEmpty();
    }

    /**
     * Gets other box colliders in the scene that are colliding with this one, not
     * including itself
     * 
     * @return ArrayList of colliding box colliders
     */
    public ArrayList<BoxCollider> getCollidingBoxColliders() {
        ArrayList<BoxCollider> collidingBoxColliders = new ArrayList<BoxCollider>();

        for (BoxCollider boxCollider : getGameObject().getScene().getComponents(BoxCollider.class)) {
            if (boxCollider != this && boxCollider.collides(this)) {
                collidingBoxColliders.add(boxCollider);
            }
        }

        return collidingBoxColliders;
    }

    /**
     * Get GameObjects of colliding box colliders in the scene, not including itself
     * 
     * @return ArrayList of colliding GameObjects
     */
    public ArrayList<GameObject> getCollidingObjects() {
        ArrayList<GameObject> collidingGameObjects = new ArrayList<GameObject>();

        for (BoxCollider boxCollider : getCollidingBoxColliders()) {
            collidingGameObjects.add(boxCollider.getGameObject());
        }

        return collidingGameObjects;
    }

    /**
     * @return if the collider is colliding with any other box colliders in the
     *         scene
     */
    public boolean isCollidingBoxColliders() {
        return !getCollidingBoxColliders().isEmpty();
    }

    /**
     * 
     * @param other
     *            the other box collider to check collision with
     * @return if the collider is colliding with the other collider
     */
    public boolean collides(BoxCollider other) {
        return getRight() >= other.getLeft()
                && getLeft() <= other.getRight()
                && getTop() >= other.getBottom()
                && getBottom() <= other.getTop();
    }

    /**
     * @return The x coordinate of the left side of the collider
     */
    public double getLeft() {
        Transform transform = getGameObject().getTransform();
        return transform.getLeft();
    }

    /**
     * @return The x coordinate of the right side of the collider
     */
    public double getRight() {
        Transform transform = getGameObject().getTransform();
        return transform.getRight();
    }

    /**
     * @return The y coordinate of the top side of the collider
     */
    public double getTop() {
        Transform transform = getGameObject().getTransform();
        return transform.getTop();
    }

    /**
     * @return The y coordinate of the bottom side of the collider
     */
    public double getBottom() {
        Transform transform = getGameObject().getTransform();
        return transform.getBottom();
    }

    /**
     * @return The position of the collider
     */
    public Vector getPosition() {
        Transform transform = getGameObject().getTransform();
        return transform.getPosition();
    }

    /**
     * @return The scale of the collider
     */
    public Vector getScale() {
        Transform transform = getGameObject().getTransform();
        return transform.getScale();
    }
}
