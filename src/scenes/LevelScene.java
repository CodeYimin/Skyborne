package scenes;

import java.awt.event.KeyEvent;

import components.Camera;
import components.KeyboardMotionController;
import components.SpriteRenderer;
import components.Tilemap;
import components.TilemapMotion;
import components.TilemapRenderer;
import components.Transform;
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
        tilemap.addComponent(new Tilemap(new int[][] {
                { 0, 1, 0 },
                { 1, 1, 1 },
                { 0, 1, 0 }
        }));
        tilemap.addComponent(new TilemapRenderer());
        addGameObject(tilemap);

        GameObject player = new GameObject();
        player.addComponent(new Transform(new Vector(0, -1), Vector.ONE));
        player.addComponent(new SpriteRenderer(new Sprite("../assets/big_demon_idle_anim_f0.png")));
        player.addComponent(new TilemapMotion());
        player.addComponent(new KeyboardMotionController(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, 5));
        addGameObject(player);

        GameObject camera = new GameObject();
        camera.addComponent(new Camera(player));
        addGameObject(camera);
    }
}
