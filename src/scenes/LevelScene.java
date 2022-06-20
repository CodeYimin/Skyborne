package scenes;

import java.awt.event.KeyEvent;

import components.Camera;
import components.DamageOverlayUI;
import components.Dungeon;
import components.DungeonMinimapUI;
import components.Enemy;
import components.HealthUI;
import components.KeyboardMotionController;
import components.MouseFire;
import components.MouseRotation;
import components.Player;
import components.SpriteRenderer;
import components.UICamera;
import components.Weapon;
import core.Game;
import core.GameObject;
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
        player.addComponent(new HealthUI());
        player.addComponent(new DamageOverlayUI());
        addGameObject(player);

        GameObject camera = new GameObject();
        camera.addComponent(new Camera(player, 50));
        addGameObject(camera);

        GameObject UICamera = new GameObject();
        UICamera.addComponent(new UICamera());
        addGameObject(UICamera);

        GameObject playerWeapon = new GameObject();
        playerWeapon.addComponent(new Weapon(Enemy.class, 10, 100));
        playerWeapon.addComponent(new SpriteRenderer(new Sprite("../assets/flask_green.png")));
        playerWeapon.addComponent(new MouseRotation());
        playerWeapon.addComponent(new MouseFire());
        playerWeapon.setParent(player);
        player.getComponent(Player.class).equip(playerWeapon);
        addGameObject(playerWeapon);
    }

    @Override
    public void start() {
        super.start();

        GameObject dungeon = new GameObject();
        dungeon.addComponent(new Dungeon());
        dungeon.addComponent(new DungeonMinimapUI());
        addGameObject(dungeon, 0);
    }
}
