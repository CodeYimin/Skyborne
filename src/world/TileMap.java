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

        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                Vector tileScreenPos = mapScreenPos
                        .add(new Vector(x, y).multiply(tileSize).multiply(camera.getZoom()))
                        .subtract(new Vector(0, mapScreenSize.getHeight()));

                g.setColor(Color.BLACK);
                g.drawImage(Tile.getSprite(tiles[y][x]).getImage(),
                        (int) tileScreenPos.getX(),
                        (int) tileScreenPos.getY(),
                        (int) tileScreenSize,
                        (int) tileScreenSize,
                        null);
            }
        }
    }

    public ArrayList<Integer> getCollidingTiles(Entity entity) {
        Vector entityTopLeft = entity.getPosition()
                .add(new Vector(-entity.getSize().getX() / 2, entity.getSize().getY()));
        Vector entityBottomRight = entity.getPosition().add(new Vector(entity.getSize().getX() / 2, 0));

        ArrayList<Integer> collidingTiles = new ArrayList<>();
        for (int x = (int) entityTopLeft.getX(); x <= entityBottomRight.getX(); x++) {
            for (int y = (int) entityBottomRight.getY(); y <= entityTopLeft.getY(); y++) {
                if (x < 0 || x >= tiles.length || y < 0 || y >= tiles[0].length) {
                    continue;
                }

                if (tiles[x][tiles[0].length - 1 - y] != 0) {
                    collidingTiles.add(tiles[x][tiles[0].length - 1 - y]);
                }
            }
        }

        return collidingTiles;
    }
}
