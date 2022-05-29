package world;

import graphics.Sprite;

public class Tile {
    public static final int DIRT = 0;
    public static final int GRASS = 1;
    public static final int STONE = 2;

    private static final Sprite[] sprites = new Sprite[] {
            // new Sprite("dirt.png"),
            new Sprite("../assets/blocks/grass.png"),
            new Sprite("../assets/blocks/stone.png")
    };

    public static Sprite getSprite(int id) {
        return sprites[id];
    }
}
