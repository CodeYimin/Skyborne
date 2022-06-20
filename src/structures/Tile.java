package structures;

public class Tile {
    public static final int AIR = 0;
    public static final int FLOOR = 1;
    public static final int WALL = 2;
    public static final int DOOR = 3;

    private static final Sprite[] sprites = new Sprite[] {
            null,
            new Sprite("../assets/floor_1.png"),
            new Sprite("../assets/wall_mid.png"),
            new Sprite("../assets/wall_hole_1.png")
    };

    private static final boolean[] solid = new boolean[] {
            false,
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
