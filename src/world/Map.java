package world;

import entities.RenderableObject;
import graphics.renderers.Renderer;
import graphics.renderers.TileMapRenderer;

public class Map extends RenderableObject {
    private boolean[][] tileMap;

    public Map(boolean[][] tileMap) {
        this.tileMap = tileMap;

        Renderer renderer = new TileMapRenderer(tileMap);
        setRenderer(renderer);
    }

}
