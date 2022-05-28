package world;

import entities.RenderableObject;
import graphics.renderers.Renderer;
import graphics.renderers.TileMapRenderer;

public class Map extends RenderableObject {
    private int[][] floorMap;
    private int[][] surfaceMap;

    public Map(boolean[][] tileMap) {
        // this.tileMap = tileMap;

        Renderer renderer = new TileMapRenderer(tileMap);
        setRenderer(renderer);
    }

}
