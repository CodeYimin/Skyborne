package world;

import java.awt.Graphics;
import java.util.ArrayList;

import entities.Hitbox;
import graphics.Camera;
import graphics.Renderable;
import util.Vector;

public class Tilemap implements Renderable {
    private Tile[][] tiles;

    public Tilemap(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public Tilemap(int[][] tileIds) {
        this.tiles = new Tile[tileIds.length][tileIds[0].length];
        for (int i = 0; i < tileIds.length; i++) {
            for (int j = 0; j < tileIds[0].length; j++) {
                this.tiles[i][j] = new Tile(tileIds[i][j]);
            }
        }
    }

    @Override
    public void render(Graphics g, Camera camera) {
        double tileScreenSize = camera.getZoom();

        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                // Top left corner
                Vector tilePosition = new Vector(x, y + 1);
                Vector tileScreenPosition = camera.worldToScreenPosition(tilePosition);

                g.drawImage(tiles[x][y].getSprite().getImage(),
                        (int) tileScreenPosition.x(),
                        (int) tileScreenPosition.y(),
                        (int) tileScreenSize,
                        (int) tileScreenSize,
                        null);
            }
        }
    }

    public ArrayList<Tile> getCollidingTiles(Hitbox hitbox) {
        ArrayList<Tile> collidingTiles = new ArrayList<>();

        for (int x = (int) Math.ceil(hitbox.left()) - 1; x <= hitbox.right(); x++) {
            for (int y = (int) Math.ceil(hitbox.bottom()) - 1; y <= hitbox.top(); y++) {
                Tile tile = getTileAt(x, y);
                if (tile != null && tile.isSolid()) {
                    collidingTiles.add(tile);
                }
            }
        }

        return collidingTiles;
    }

    public ArrayList<Tile> getIntersectingTiles(Hitbox hitbox) {
        ArrayList<Tile> intersectingTiles = new ArrayList<>();

        for (int x = (int) hitbox.left(); x < hitbox.right(); x++) {
            for (int y = (int) hitbox.bottom(); y < hitbox.top(); y++) {
                Tile tile = getTileAt(x, y);
                if (tile != null && tile.isSolid()) {
                    intersectingTiles.add(tile);
                }
            }
        }

        return intersectingTiles;
    }

    public Tile getTileAt(int x, int y) {
        if (x < 0 || x >= tiles.length || y < 0 || y >= tiles[0].length) {
            return null;
        }

        return tiles[x][y];
    }

    public Tile getTileAt(Vector position) {
        return getTileAt((int) position.x(), (int) position.y());
    }

    public void setTile(int x, int y, Tile tile) {
        tiles[x][y] = tile;
    }
}
