package graphics.renderers;

import java.awt.Color;
import java.awt.Graphics;

import graphics.Sprite;
import util.Size;
import util.Vector;
import world.Tile;

public class TileMapRenderer implements Renderer {
    private int[][] tileMap;
    private Sprite sprite = new Sprite("../assets/grass.png");

    public TileMapRenderer(int[][] tileMap) {
        this.tileMap = tileMap;
    }

    @Override
    public void render(Graphics g, RenderInfo renderInfo) {
        Size tileSize = new Size(1, 1);
        Size tileScreenSize = tileSize.multiply(renderInfo.zoom);
        Size mapSize = new Size(tileMap[0].length, tileMap.length);
        Size mapScreenSize = mapSize.multiply(renderInfo.zoom);
        Vector mapScreenPos = renderInfo.objectScreenPos.subtract(new Vector(mapScreenSize).divide(2));

        for (int y = 0; y < tileMap.length; y++) {
            for (int x = 0; x < tileMap[0].length; x++) {
                Vector tileScreenPos = mapScreenPos
                        .add(new Vector(x, y).multiply(new Vector(tileSize)).multiply(renderInfo.zoom));

                g.setColor(Color.BLACK);
                g.drawImage(Tile.getSprite(tileMap[y][x]).getImage(),
                        (int) tileScreenPos.getX(),
                        (int) tileScreenPos.getY(),
                        (int) tileScreenSize.getWidth(),
                        (int) tileScreenSize.getHeight(),
                        null);
            }
        }
    }
}
