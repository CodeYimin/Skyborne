package collision;

import java.util.ArrayList;

import world.Tile;

public class TileMapCollisionInfo extends CollisionInfo {
    public final ArrayList<Tile> collidedTiles;

    public TileMapCollisionInfo(ArrayList<Tile> collidedTiles) {
        this.collidedTiles = collidedTiles;
    }
}
