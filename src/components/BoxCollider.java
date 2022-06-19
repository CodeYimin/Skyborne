package components;

import java.util.ArrayList;

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

    public ArrayList<Tile> getCollidingTiles() {
        ArrayList<Tilemap> tilemaps = getGameObject().getScene().getComponents(Tilemap.class);
        ArrayList<Tile> collidingTiles = new ArrayList<Tile>();

        for (Tilemap tilemap : tilemaps) {
            collidingTiles.addAll(tilemap.getCollidingTiles(this));
        }

        return collidingTiles;
    }

    public boolean intersectsWithTiles() {
        return !getIntersectingTiles().isEmpty();
    }

    public boolean collidesWithTiles() {
        return !getCollidingTiles().isEmpty();
    }

    public boolean intersects(BoxCollider other) {

        return getRight() > other.getLeft()
                && getLeft() < other.getRight()
                && getTop() < other.getBottom()
                && getBottom() > other.getTop();
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
