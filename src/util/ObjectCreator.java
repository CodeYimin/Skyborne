package util;

import components.AutoFire;
import components.BoxCollider;
import components.DestroyOnDeath;
import components.Enemy;
import components.EnemyAim;
import components.EnemyMotionController;
import components.Health;
import components.MotionSpriteFlipper;
import components.Player;
import components.Room;
import components.SpriteRenderer;
import components.Tilemap;
import components.TilemapMotion;
import components.Transform;
import components.Weapon;
import core.GameObject;
import structures.IntVector;
import structures.Tile;
import structures.Vector;

public class ObjectCreator {
    public static GameObject createTilemapCreature(Vector position, Vector size, int health, String spritePath) {
        GameObject gameObject = new GameObject();
        gameObject.addComponent(new Transform(position, size));
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
        enemy.addComponent(new EnemyMotionController(target, 1));
        enemy.addComponent(new DestroyOnDeath());
        return enemy;
    }

    public static GameObject createEnemyWeapon(GameObject parent, GameObject target, Vector size, String spritePath, int damage, int fireInterval,
            double bulletSpeed) {
        GameObject enemyWeapon = new GameObject();
        enemyWeapon.addComponent(new Weapon(Player.class, 5, fireInterval));
        enemyWeapon.addComponent(new Transform(Vector.ZERO, size));
        enemyWeapon.addComponent(new SpriteRenderer(spritePath));
        enemyWeapon.addComponent(new EnemyAim(target));
        enemyWeapon.addComponent(new AutoFire(fireInterval));
        enemyWeapon.setParent(parent);

        return enemyWeapon;
    }

    public static GameObject createRoom(IntVector position, String mapPath) {
        GameObject gameObject = new GameObject();
        gameObject.addComponent(new Transform(position.toVector().multiply(Const.DISTANCE_BETWEEN_ROOMS)));
        Tilemap tilemap = new Tilemap(mapPath);
        gameObject.addComponent(tilemap);
        gameObject.addComponent(new Room(tilemap, 1, 2, 3));
        return gameObject;
    }

    public static GameObject createHallway(GameObject room1, GameObject room2) {
        Transform transform1 = room1.getComponent(Transform.class);
        Transform transform2 = room2.getComponent(Transform.class);
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
        Vector scale;

        if (direction == 0) {
            position = new Vector((position1.getX() + position2.getX()) / 2, position2.getY());
            scale = new Vector(Math.abs(position1.getX() - position2.getX()) - tilemap1.getWidth() / 2 - tilemap2.getWidth() / 2,
                    Const.HALLWAY_WIDTH + 2);
        } else {
            position = new Vector(position2.getX(), (position1.getY() + position2.getY()) / 2);
            scale = new Vector(Const.HALLWAY_WIDTH + 2,
                    Math.abs(position1.getY() - position2.getY()) - tilemap1.getHeight() / 2 - tilemap2.getHeight() / 2);
        }

        Tile[][] tiles = new Tile[(int) scale.getX()][(int) scale.getY()];
        for (int x = 0; x < scale.getX(); x++) {
            for (int y = 0; y < scale.getY(); y++) {
                if (direction == 0) {
                    if (y == 0 || y == scale.getY() - 1) {
                        tiles[x][y] = new Tile(2);
                    } else {
                        tiles[x][y] = new Tile(1);
                    }
                } else if (direction == 1) {
                    if (x == 0 || x == scale.getX() - 1) {
                        tiles[x][y] = new Tile(2);
                    } else {
                        tiles[x][y] = new Tile(1);
                    }
                }
            }
        }

        GameObject gameObject = new GameObject();
        gameObject.addComponent(new Transform(position, scale));
        Tilemap tilemap = new Tilemap(tiles);
        gameObject.addComponent(tilemap);
        return gameObject;
    }
}
