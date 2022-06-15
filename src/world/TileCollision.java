package world;

import util.Side;

public class TileCollision {
    private final Tile tile;
    private final Side perfectlyCollidingSide;

    public TileCollision(Tile tile, Side perfectlyCollidingSide) {
        this.tile = tile;
        this.perfectlyCollidingSide = perfectlyCollidingSide;
    }

    public Tile getTile() {
        return tile;
    }

    public Side getSideAlignedWithTile() {
        return perfectlyCollidingSide;
    }
}
