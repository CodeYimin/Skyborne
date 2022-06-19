package scenes;

import java.awt.event.KeyEvent;

import components.AutoFire;
import components.BoxCollider;
import components.Camera;
import components.DestroyOnDeath;
import components.Enemy;
import components.EnemyAim;
import components.EnemyMotionController;
import components.Health;
import components.KeyboardMotionController;
import components.MotionSpriteFlipper;
import components.MouseFire;
import components.MouseRotation;
import components.Player;
import components.SpriteRenderer;
import components.Tilemap;
import components.TilemapMotion;
import components.TilemapRenderer;
import components.Transform;
import components.Weapon;
import core.Game;
import core.GameObject;
import structures.Sprite;
import structures.Vector;

public class LevelScene extends Scene {
    public LevelScene(Game game) {
        super(game);
    }

    @Override
    public void init() {
        GameObject tilemap = new GameObject();
        tilemap.addComponent(new Transform(new Vector(0, 0)));
        tilemap.addComponent(new Tilemap("../data/tilemap.txt"));
        tilemap.addComponent(new TilemapRenderer());
        addGameObject(tilemap);

        GameObject player = new GameObject();
        player.addComponent(new Player());
        player.addComponent(new Health(10));
        player.addComponent(new Transform(new Vector(0, 0), Vector.ONE));
        player.addComponent(new BoxCollider());
        player.addComponent(new SpriteRenderer(new Sprite("../assets/big_demon_idle_anim_f0.png")));
        player.addComponent(new TilemapMotion());
        player.addComponent(new KeyboardMotionController(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, 5));
        player.addComponent(new MotionSpriteFlipper());
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

        GameObject enemy = new GameObject();
        enemy.addComponent(new Enemy());
        enemy.addComponent(new Health(10));
        enemy.addComponent(new Transform(new Vector(0, 1)));
        enemy.addComponent(new BoxCollider());
        enemy.addComponent(new SpriteRenderer(new Sprite("../assets/masked_orc_idle_anim_f0.png")));
        enemy.addComponent(new TilemapMotion());
        enemy.addComponent(new EnemyMotionController(player, 1));
        enemy.addComponent(new MotionSpriteFlipper());
        enemy.addComponent(new DestroyOnDeath());
        addGameObject(enemy);

        GameObject enemyWeapon = new GameObject();
        enemyWeapon.addComponent(new Weapon(Player.class, 5, 100));
        enemyWeapon.addComponent(new Transform(Vector.ZERO, Vector.ONE.multiply(0.8)));
        enemyWeapon.addComponent(new SpriteRenderer(new Sprite("../assets/flask_green.png")));
        enemyWeapon.addComponent(new EnemyAim(player));
        enemyWeapon.addComponent(new AutoFire(500));
        enemyWeapon.setParent(enemy);
        addGameObject(enemyWeapon);
    }
}
