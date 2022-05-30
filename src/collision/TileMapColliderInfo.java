package collision;

import java.util.ArrayList;

import entities.GameObject;
import world.Tile;

public class TileMapColliderInfo extends ColliderInfo {
    public final ArrayList<Tile> collidedTiles;

    public TileMapColliderInfo(GameObject gameObject, ArrayList<Tile> tiles) {
        super(gameObject);
        this.collidedTiles = tiles;
    }
}
