package world;

import graphics.Sprite;

public class Tile {
    public static final int DIRT = 0;
    public static final int GRASS = 1;

    public static final Sprite[] sprites = new Sprite[] {
            // new Sprite("dirt.png"),
            new Sprite("../assets/tiles/grass.png")
    };

    private int id;

    public Tile(int id) {
        this.id = id;
    }

    public Sprite getSprite() {
        return sprites[id];
    }
}
