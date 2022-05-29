package world;

import core.UpdateInfo;
import entities.GameObject;
import graphics.renderers.Renderer;
import graphics.renderers.TileMapRenderer;

public class TileMap extends GameObject {
    private int[][] tiles;

    public TileMap(int[][] tiles) {
        this.tiles = tiles;

        Renderer renderer = new TileMapRenderer(tiles);
        setRenderer(renderer);
    }

    public void update(UpdateInfo updateInfo) {
        // Do nothing
    }
}
