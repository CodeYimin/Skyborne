package util;

import components.AutoFire;
import components.BoxCollider;
import components.DestroyOnDeath;
import components.Enemy;
import components.EnemyAim;
import components.EnemyMotionController;
import components.HPBar;
import components.Health;
import components.MotionSpriteFlipper;
import components.Player;
import components.Room;
import components.SpriteRenderer;
import components.Tilemap;
import components.TilemapMotion;
import components.Transform;
import components.UndiscoveredRoomOverlay;
import components.Weapon;
import core.GameObject;
import structures.IntVector;
import structures.Tile;
import structures.Vector;

public class ObjectCreator {
    public static GameObject createTilemapCreature(Vector position, Vector size, int health, String spritePath) {
        GameObject gameObject = new GameObject();
        gameObject.addComponent(new SpriteRenderer(spritePath));
        gameObject.addComponent(new BoxCollider());
        gameObject.addComponent(new Health(health));
        gameObject.addComponent(new TilemapMotion());
        gameObject.addComponent(new MotionSpriteFlipper());
        return gameObject;
    }

    public static GameObject createEnemy(GameObject target, Vector position, Vector size, int health, String spritePath) {
        GameObject enemy = ObjectCreator.createTilemapCreature(position, size, health, spritePath);
        enemy.addComponent(new Enemy());
        enemy.addComponent(new EnemyMotionController(1));
        enemy.addComponent(new DestroyOnDeath());
        enemy.addComponent(new HPBar());
        return enemy;
    }

    public static GameObject createEnemyWeapon(GameObject parent, GameObject target, Vector size, String spritePath, int damage, int fireInterval,
            double bulletSpeed) {
        GameObject enemyWeapon = new GameObject();
        enemyWeapon.addComponent(new Weapon(Player.class, bulletSpeed, 0, fireInterval));
        enemyWeapon.addComponent(new SpriteRenderer(spritePath));
        enemyWeapon.addComponent(new EnemyAim(target));
        enemyWeapon.addComponent(new AutoFire(fireInterval));
        enemyWeapon.setParent(parent);

        return enemyWeapon;
    }

    public static GameObject createRoom(IntVector position, String mapPath, int maxEnemies) {
        GameObject gameObject = new GameObject();
        gameObject.getTransform().setPosition(position.toVector().multiply(Const.DUNGEON_DISTANCE_BETWEEN_ROOMS));
        gameObject.addComponent(new Room(mapPath, 1, 2, 3, maxEnemies));
        gameObject.addComponent(new UndiscoveredRoomOverlay());
        return gameObject;
    }

    public static GameObject createHallway(GameObject room1, GameObject room2) {
        Transform transform1 = room1.getTransform();
        Transform transform2 = room2.getTransform();
        Vector position1 = transform1.getPosition();
        Vector position2 = transform2.getPosition();
        Tilemap tilemap1 = room1.getComponent(Tilemap.class);
        Tilemap tilemap2 = room2.getComponent(Tilemap.class);

        // 0 = x, 1 = y
        int direction = 0;
        if (position1.getX() == position2.getX()) {
            direction = 1;
        }

        Vector position;
        Vector size;

        if (direction == 0) {
            double x1 = Math.min(position1.getX() + tilemap1.getWidth() / 2.0, position2.getX() + tilemap2.getWidth() / 2.0);
            double x2 = Math.max(position1.getX() - tilemap1.getWidth() / 2.0, position2.getX() - tilemap2.getWidth() / 2.0);
            position = new Vector((x1 + x2) / 2, position2.getY());
            size = new Vector(Math.abs(position1.getX() - position2.getX()) - tilemap1.getWidth() / 2 - tilemap2.getWidth() / 2,
                    Const.DUNGEON_HALLWAY_WIDTH + 2);
        } else {
            double y1 = Math.min(position1.getY() + tilemap1.getHeight() / 2, position2.getY() + tilemap2.getHeight() / 2);
            double y2 = Math.max(position1.getY() - tilemap1.getHeight() / 2, position2.getY() - tilemap2.getHeight() / 2);
            position = new Vector(position2.getX(), (y1 + y2) / 2);
            size = new Vector(Const.DUNGEON_HALLWAY_WIDTH + 2,
                    Math.abs(position1.getY() - position2.getY()) - tilemap1.getHeight() / 2 - tilemap2.getHeight() / 2);
        }

        Tile[][] tiles = new Tile[(int) size.getX()][(int) size.getY()];
        for (int x = 0; x < size.getX(); x++) {
            for (int y = 0; y < size.getY(); y++) {
                if (direction == 0) {
                    if (y == 0 || y == size.getY() - 1) {
                        tiles[x][y] = new Tile(2);
                    } else {
                        tiles[x][y] = new Tile(1);
                    }
                } else if (direction == 1) {
                    if (x == 0 || x == size.getX() - 1) {
                        tiles[x][y] = new Tile(2);
                    } else {
                        tiles[x][y] = new Tile(1);
                    }
                }
            }
        }

        GameObject gameObject = new GameObject();
        gameObject.getTransform().setPosition(position);
        gameObject.addComponent(new Tilemap(tiles));
        return gameObject;
    }
}
