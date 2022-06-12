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

    private static final boolean[] solid = new boolean[] {
            false,
            true,
            true
    };

    private final int id;

    public Tile(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Sprite getSprite() {
        return sprites[id];
    }

    public boolean isSolid() {
        return solid[id];
    }
}
