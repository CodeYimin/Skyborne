package scenes;

import java.awt.event.KeyEvent;

import components.Camera;
import components.DamageOverlayUI;
import components.DestroyOnDeath;
import components.Dungeon;
import components.DungeonMinimapUI;
import components.Enemy;
import components.KeyboardMotionController;
import components.Mana;
import components.Player;
import components.PlayerStatsUI;
import components.SpriteRenderer;
import components.UICamera;
import components.Weapon;
import core.Game;
import core.GameObject;
import structures.Sprite;
import structures.Vector;
import util.Const;
import util.ObjectCreator;

public class LevelScene extends Scene {
    public LevelScene(Game game) {
        super(game);
    }

    @Override
    public void init() {
        GameObject player = ObjectCreator.createTilemapCreature(10, Const.PLAYER_SPRITE_PATH);
        player.addComponent(new Player());
        player.addComponent(new Mana(200));
        player.addComponent(new KeyboardMotionController(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, 5));
        player.addComponent(new PlayerStatsUI());
        player.addComponent(new DestroyOnDeath());
        player.addComponent(new DamageOverlayUI());
        addGameObject(player);

        GameObject camera = new GameObject();
        camera.addComponent(new Camera(player, 50));
        addGameObject(camera);

        GameObject UICamera = new GameObject();
        UICamera.addComponent(new UICamera());
        addGameObject(UICamera);

        GameObject playerWeapon = new GameObject();
        playerWeapon.addComponent(new Weapon(Enemy.class, 3, 10, 1, 500));
        playerWeapon.addComponent(new SpriteRenderer(new Sprite(Const.WEAPON_SPRITE_PATH)));
        playerWeapon.getTransform().setLocalScale(Vector.ONE.multiply(0.5));
        addGameObject(playerWeapon);
        player.getComponent(Player.class).equipWeapon(playerWeapon);

        GameObject dungeon = new GameObject();
        dungeon.addComponent(new Dungeon(player));
        dungeon.addComponent(new DungeonMinimapUI());
        addGameObject(dungeon, 0);
    }
}
