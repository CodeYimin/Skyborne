package util;

public class Const {
    // Game
    public static final int FPS = 120;
    public static final String BASE_ASSETS_PATH = "../assets/";
    public static final String BASE_DATA_PATH = "../data/";

    // Dungeon
    public static final int DUNGEON_DISTANCE_BETWEEN_ROOMS = 35;
    public static final int DUNGEON_HALLWAY_WIDTH = 4;
    public static final int DUNGEON_WIDTH = 5;
    public static final int DUNGEON_HEIGHT = 5;
    public static final int DUNGEON_MIN_ROOMS = 5;
    public static final int DUNGEON_MAX_ROOMS = 10;

    // Dungeon Minimap
    public static final int DUNGEON_MINIMAP_WIDTH = 200;
    public static final int DUNGEON_MINIMAP_HEIGHT = 200;
    public static final int DUNGEON_MINIMAP_MARGIN = 20;

    // Dungeon Tiles
    public static final String DUNGEON_FLOOR_PATH = BASE_ASSETS_PATH + "floor.png";
    public static final String DUNGEON_WALL_PATH = BASE_ASSETS_PATH + "wall.png";
    public static final String DUNGEON_DOOR_PATH = BASE_ASSETS_PATH + "door.png";

    // Dungeon Room
    public static final String DUNGEON_STARTING_ROOM_PATH = BASE_DATA_PATH + "room0.txt";
    public static final String[] DUNGEON_ROOM_PATHS = { BASE_DATA_PATH + "room1.txt", BASE_DATA_PATH + "room2.txt" };

    // Player Stats UI
    public static final int PLAYER_STATS_UI_WIDTH = 200;
    public static final int PLAYER_STATS_UI_HEIGHT = 70;
    public static final int PLAYER_STATS_UI_MARGIN = 20;
    public static final int PLAYER_STATS_UI_PADDING = 10;
    public static final int PLAYER_STATS_UI_MARGIN_BETWEEN_STATS = 10;

    // Enemy
    public static final String ENEMY_SPRITE_PATH = BASE_ASSETS_PATH + "enemy.png";
    public static final int MAX_ENEMY_MANA_ORB_DROPS = 3;
    public static final int MANA_PER_ENEMY_MANA_ORB = 3;
    public static final double ENEMY_HEALTH_ORB_DROP_CHANCE = 0.25;

    // Orbs
    public static final String MANA_ORB_SPRITE_PATH = BASE_ASSETS_PATH + "mana_orb.png";
    public static final String HEALTH_ORB_SPRITE_PATH = BASE_ASSETS_PATH + "health_orb.png";

    // Player
    public static final String PLAYER_SPRITE_PATH = BASE_ASSETS_PATH + "player.png";

    // Weapon
    public static final String WEAPON_SPRITE_PATH = BASE_ASSETS_PATH + "weapon.png";
    public static final String BULLET_SPRITE_PATH = BASE_ASSETS_PATH + "bullet.png";
}
