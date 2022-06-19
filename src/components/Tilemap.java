package components;

import java.io.File;
import java.util.ArrayList;

import structures.Tile;
import structures.Vector;
import util.FileUtils;

public class Tilemap extends Component {
    private Tile[][] tiles;

    public Tilemap(Tile[][] tiles) {
        this.tiles = tiles;
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

    @Override
    public void start() {
        Transform transform = getGameObject().getComponent(Transform.class);
        if (transform == null) {
            return;
        }

        transform.setScale(new Vector(tiles.length, tiles[0].length));
    }

    public ArrayList<Tile> getCollidingTiles(BoxCollider boxCollider) {
        ArrayList<Tile> collidingTiles = new ArrayList<>();

        for (int x = (int) Math.ceil(boxCollider.getLeft()) - 1; x <= boxCollider.getRight(); x++) {
            for (int y = (int) Math.ceil(boxCollider.getBottom()) - 1; y <= boxCollider.getTop(); y++) {
                Tile tile = getTileAtWorld(x, y);
                if (tile != null && tile.isSolid()) {
                    collidingTiles.add(tile);
                }
            }
        }

        return collidingTiles;
    }

    public ArrayList<Tile> getIntersectingTiles(BoxCollider boxCollider) {
        ArrayList<Tile> intersectingTiles = new ArrayList<>();

        for (int x = (int) Math.floor(boxCollider.getLeft()); x < boxCollider.getRight(); x++) {
            for (int y = (int) Math.floor(boxCollider.getBottom()); y < boxCollider.getTop(); y++) {
                Tile tile = getTileAtWorld(x, y);
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

    public Tile getTileAtWorld(Vector position) {
        Transform transform = getGameObject().getComponent(Transform.class);
        return getTile(position.subtract(transform.getPosition()).add(transform.getScale().divide(2)));
    }

    public Tile getTileAtWorld(double x, double y) {
        return getTileAtWorld(new Vector(x, y));
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || x >= tiles.length || y < 0 || y >= tiles[0].length) {
            return null;
        }
        return tiles[x][y];
    }

    public Tile getTile(Vector position) {
        return getTile((int) Math.floor(position.getX()), (int) Math.floor(position.getY()));
    }

    public void setTile(int x, int y, Tile tile) {
        tiles[x][y] = tile;
    }

    public Vector getTileWorldPosition(int x, int y) {
        Transform transform = getGameObject().getComponent(Transform.class);
        return transform.getPosition().add(x, y).subtract(transform.getScale().divide(2));
    }
}
