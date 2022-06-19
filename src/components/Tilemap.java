package components;

import java.io.File;
import java.util.ArrayList;

import structures.Tile;
import util.FileUtils;

public class Tilemap extends Component {
    private Tile[][] tiles;

    public Tilemap(Tile[][] tiles) {
        setTiles(tiles);
    }

    public Tilemap(int[][] tileIds) {
        setTiles(tileIds);
    }

    public Tilemap(String filePath) {
        File file = new File(filePath);
        int[][] tileIds = FileUtils.processIntGrid(file);
        if (tileIds != null) {
            setTiles(tileIds);
        }
    }

    public ArrayList<Tile> getCollidingTiles(BoxCollider boxCollider) {
        ArrayList<Tile> collidingTiles = new ArrayList<>();

        for (int x = (int) Math.ceil(boxCollider.getLeft()) - 1; x <= boxCollider.getRight(); x++) {
            for (int y = (int) Math.ceil(boxCollider.getBottom()) - 1; y <= boxCollider.getTop(); y++) {
                Tile tile = getTileAt(x, y);
                if (tile != null && tile.isSolid()) {
                    collidingTiles.add(tile);
                }
            }
        }

        return collidingTiles;
    }

    public ArrayList<Tile> getIntersectingTiles(BoxCollider boxCollider) {
        ArrayList<Tile> intersectingTiles = new ArrayList<>();

        for (int x = (int) boxCollider.getLeft(); x < boxCollider.getRight(); x++) {
            for (int y = (int) boxCollider.getBottom(); y < boxCollider.getTop(); y++) {
                Tile tile = getTileAt(x, y);
                if (tile != null && tile.isSolid()) {
                    intersectingTiles.add(tile);
                }
            }
        }

        return intersectingTiles;
    }

    public Tile[][] getTiles() {
        return tiles.clone();
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public void setTiles(int[][] tileIds) {
        this.tiles = new Tile[tileIds.length][tileIds[0].length];
        for (int i = 0; i < tileIds.length; i++) {
            for (int j = 0; j < tileIds[0].length; j++) {
                this.tiles[i][j] = new Tile(tileIds[i][j]);
            }
        }
    }

    public Tile getTileAt(int x, int y) {
        if (x < 0 || x >= tiles.length || y < 0 || y >= tiles[0].length) {
            return null;
        }
        return tiles[x][y];
    }

    public void setTile(int x, int y, Tile tile) {
        tiles[x][y] = tile;
    }
}
