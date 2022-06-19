package scenes;

import java.awt.event.KeyEvent;

import components.Camera;
import components.Enemy;
import components.KeyboardMotionController;
import components.MouseFire;
import components.MouseRotation;
import components.Player;
import components.SpriteRenderer;
import components.Transform;
import components.Weapon;
import core.Game;
import core.GameObject;
import structures.IntVector;
import structures.Sprite;
import structures.Vector;
import util.ObjectCreator;

public class LevelScene extends Scene {
    public LevelScene(Game game) {
        super(game);
    }

    @Override
    public void init() {

        GameObject player = ObjectCreator.createTilemapCreature(new Vector(0, 0), Vector.ONE, 10, "../assets/big_demon_idle_anim_f0.png");
        player.addComponent(new Player());
        player.addComponent(new KeyboardMotionController(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, 5));
        addGameObject(player);

        GameObject camera = new GameObject();
        camera.addComponent(new Camera(player, 50));
        addGameObject(camera);

        GameObject playerWeapon = new GameObject();
        playerWeapon.addComponent(new Weapon(Enemy.class, 10, 100));
        playerWeapon.addComponent(new Transform(Vector.ZERO, Vector.ONE.multiply(0.8)));
        playerWeapon.addComponent(new SpriteRenderer(new Sprite("../assets/flask_green.png")));
        playerWeapon.addComponent(new MouseRotation());
        playerWeapon.addComponent(new MouseFire());
        playerWeapon.setParent(player);
        addGameObject(playerWeapon);

        GameObject enemy = ObjectCreator.createEnemy(player, new Vector(0, 0), Vector.ONE, 10, "../assets/masked_orc_idle_anim_f0.png");
        addGameObject(enemy);

        GameObject enemyWeapon = ObjectCreator.createEnemyWeapon(
                enemy, player,
                Vector.ONE.multiply(0.8),
                "../assets/flask_green.png",
                1, 500, 5);
        addGameObject(enemyWeapon);

        GameObject enemy2 = ObjectCreator.createEnemy(player, new Vector(30, 0), Vector.ONE, 10, "../assets/masked_orc_idle_anim_f0.png");
        addGameObject(enemy2);

        GameObject enemy2Weapon = ObjectCreator.createEnemyWeapon(
                enemy2, player,
                Vector.ONE.multiply(0.8),
                "../assets/flask_green.png",
                1, 500, 5);
        addGameObject(enemy2Weapon);
    }

    @Override
    public void start() {
        super.start();

        GameObject room1 = ObjectCreator.createRoom(new IntVector(0, 0), "../data/room1.txt");
        addGameObject(room1, 0);

        GameObject room2 = ObjectCreator.createRoom(new IntVector(1, 0), "../data/room2.txt");
        addGameObject(room2, 0);

        GameObject hallway = ObjectCreator.createHallway(room1, room2);
        addGameObject(hallway, 0);
    }
}
