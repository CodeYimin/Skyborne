package util;

import components.AutoFire;
import components.AutoRotate;
import components.BoxCollider;
import components.DestroyOnDeath;
import components.Enemy;
import components.EnemyMotionController;
import components.HPBar;
import components.Hallway;
import components.Health;
import components.MotionSpriteFlipper;
import components.Player;
import components.Room;
import components.SpriteRenderer;
import components.TilemapMotion;
import components.UndiscoveredRoomOverlay;
import components.Weapon;
import core.GameObject;
import structures.IntVector;

public class ObjectCreator {
    public static GameObject createTilemapCreature(int health, String spritePath) {
        GameObject gameObject = new GameObject();
        gameObject.addComponent(new SpriteRenderer(spritePath));
        gameObject.addComponent(new BoxCollider());
        gameObject.addComponent(new Health(health));
        gameObject.addComponent(new TilemapMotion());
        gameObject.addComponent(new MotionSpriteFlipper());
        return gameObject;
    }

    public static GameObject createEnemy(GameObject target, int health) {
        GameObject enemy = ObjectCreator.createTilemapCreature(health, "../assets/masked_orc_idle_anim_f0.png");
        enemy.addComponent(new Enemy());
        enemy.addComponent(new EnemyMotionController(1));
        enemy.addComponent(new DestroyOnDeath());
        enemy.addComponent(new HPBar());
        return enemy;
    }

    public static GameObject createEnemyWeapon(GameObject target, int damage, int fireInterval, double bulletSpeed) {
        GameObject enemyWeapon = new GameObject();
        enemyWeapon.addComponent(new Weapon(Player.class, 1, bulletSpeed, 0, fireInterval));
        enemyWeapon.addComponent(new SpriteRenderer("../assets/flask_green.png"));
        enemyWeapon.addComponent(new AutoRotate(target));
        enemyWeapon.addComponent(new AutoFire(fireInterval));

        return enemyWeapon;
    }

    public static GameObject createRoom(IntVector position, String mapPath, int maxEnemies) {
        GameObject gameObject = new GameObject();
        gameObject.getTransform().setPosition(position.toVector().multiply(Const.DUNGEON_DISTANCE_BETWEEN_ROOMS));
        gameObject.addComponent(new Room(mapPath, 1, 2, 3, maxEnemies));
        gameObject.addComponent(new UndiscoveredRoomOverlay());
        return gameObject;
    }

    public static GameObject createHallway(Room room1, Room room2) {
        GameObject hallway = new GameObject();
        hallway.addComponent(new Hallway(room1, room2, 1, 2, Const.DUNGEON_HALLWAY_WIDTH));
        return hallway;
    }
}
