package components;

import java.awt.Graphics;

import structures.Tile;
import structures.Vector;

public class TilemapRenderer extends Renderer {
    @Override
    public void render(Graphics g, Camera camera) {
        Tilemap tilemap = getGameObject().getComponent(Tilemap.class);
        if (tilemap == null) {
            return;
        }

        Tile[][] tiles = tilemap.getTiles();
        Vector tileScreenSize = Vector.ONE.multiply(camera.getZoom());

        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                // Bottom left -> Center
                Vector tilePosition = tilemap.getTileWorldPosition(x, y).add(0.5);
                Vector tileScreenPosition = camera.worldToScreenPosition(tilePosition);

                tiles[x][y].getSprite().draw(g, tileScreenPosition, tileScreenSize, 0, false, false);
            }
        }
    }
}
