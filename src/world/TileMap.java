package world;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import core.UpdateInfo;
import entities.Entity;
import entities.GameObject;
import graphics.Camera;
import graphics.Renderable;
import util.Size;
import util.Vector;

public class TileMap extends GameObject implements Renderable {
    private double tileSize = 1;
    private int[][] tiles;

    public TileMap(int[][] tiles) {
        this.tiles = tiles;
    }

    @Override
    public void update(UpdateInfo updateInfo) {
        // Do nothing
    }

    @Override
    public void render(Graphics g, Camera camera) {
        double tileScreenSize = tileSize * camera.getZoom();
        Size mapSize = new Size(tiles.length, tiles[0].length);
        Size mapScreenSize = mapSize.multiply(camera.getZoom());
        Vector mapScreenPos = camera.getScreenPosition(this);

        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[0].length; col++) {
                Vector tileScreenPos = mapScreenPos
                        .add(new Vector(row, col).multiply(tileSize).multiply(camera.getZoom()))
                        .subtract(new Vector(0, mapScreenSize.getHeight()));

                g.setColor(Color.BLACK);
                g.drawImage(Tile.getSprite(tiles[col][row]).getImage(),
                        (int) tileScreenPos.getX(),
                        (int) tileScreenPos.getY(),
                        (int) tileScreenSize,
                        (int) tileScreenSize,
                        null);
            }
        }
    }

    public ArrayList<Integer> getCollidingTiles(Vector position, Size size) {
        double top = position.getY() + size.getHeight();
        double bottom = position.getY();
        double left = position.getX() - size.getWidth() / 2;
        double right = position.getX() + size.getWidth() / 2;

        ArrayList<Integer> collidingTiles = new ArrayList<>();
        for (int x = (int) left; x < right; x++) {
            for (int y = (int) bottom; y < top; y++) {
                if (x < 0 || x >= tiles.length || y < 0 || y >= tiles[0].length) {
                    continue;
                }

                if (Tile.isSolid(tiles[x][tiles[0].length - 1 - y])) {
                    collidingTiles.add(tiles[x][tiles[0].length - 1 - y]);
                }
            }
        }

        return collidingTiles;
    }

    public ArrayList<Integer> getCollidingTiles(Entity entity) {
        return getCollidingTiles(entity.getPosition(), entity.getSize());
    }
}
