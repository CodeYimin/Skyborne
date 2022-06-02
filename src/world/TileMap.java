package world;

import java.awt.Graphics;
import java.util.ArrayList;

import entities.Hitbox;
import graphics.Camera;
import graphics.Renderable;
import util.Vector;

public class Tilemap implements Renderable {
    private int[][] tiles;

    public Tilemap(int[][] tiles) {
        this.tiles = tiles;
    }

    @Override
    public void render(Graphics g, Camera camera) {
        double tileScreenSize = camera.getZoom();

        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                // Top left corner
                Vector tilePosition = new Vector(x, y + 1);
                Vector tileScreenPosition = camera.worldToScreenPosition(tilePosition);

                g.drawImage(Tile.getSprite(tiles[x][y]).getImage(),
                        (int) tileScreenPosition.x(),
                        (int) tileScreenPosition.y(),
                        (int) tileScreenSize,
                        (int) tileScreenSize,
                        null);
            }
        }
    }

    public ArrayList<Integer> getCollidingTiles(Hitbox hitbox) {
        ArrayList<Integer> collidingTiles = new ArrayList<>();

        for (int x = (int) hitbox.left(); x < hitbox.right(); x++) {
            for (int y = (int) hitbox.bottom(); y < hitbox.top(); y++) {
                int tile = getTileAt(x, y);
                if (tile != -1 && Tile.isSolid(tile)) {
                    collidingTiles.add(tile);
                }
            }
        }

        return collidingTiles;
    }

    public int getTileAt(int x, int y) {
        if (x < 0 || x >= tiles.length || y < 0 || y >= tiles[0].length) {
            return -1;
        }

        return tiles[x][y];
    }

    public int getTileAt(Vector position) {
        return getTileAt((int) position.x(), (int) position.y());
    }

    public void setTile(int x, int y, int tile) {
        tiles[x][y] = tile;
    }
}
