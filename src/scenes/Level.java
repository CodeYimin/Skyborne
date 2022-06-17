package scenes;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import core.Game;
import core.Updatable;
import entities.Creature;
import entities.PlayerMovementController;
import graphics.AnimatedSprite;
import graphics.Camera;
import graphics.Sprite;
import input.PlayerControls;
import world.Tilemap;

public class Level implements Updatable {
    private Game game;
    private Camera camera;
    // private ZombieSpawner zombieSpawner = new ZombieSpawner(this, 2500);

    private ArrayList<Creature> creatures = new ArrayList<>();
    private Tilemap tilemap;

    public Level(Game game) {
        this.game = game;
        this.camera = new Camera(game.getWindow().getGraphicsPanel());

        this.tilemap = new Tilemap(new int[][] {
                { 1, 1, 0 },
                { 0, 1, 1 },
                { 1, 1, 0 },
                { 0, 0, 0 },
                { 1, 0, 0 },
                { 1, 0, 1 },
                { 1, 0, 1 },
                { 1, 0, 0 },
                { 1, 1, 0 },
                { 1, 1, 1 },
                { 1, 1, 0 },
                { 1, 1, 1 },
                { 1, 1, 1 },
                { 1, 1, 0 },
                { 1, 1, 0 },
                { 1, 1, 0 },
                { 1, 1, 0 },
                { 1, 1, 1 },
                { 1, 1, 1 },
        });
        this.camera.addRenderable(tilemap);

        PlayerControls playerControls = new PlayerControls(
                KeyEvent.VK_W,
                KeyEvent.VK_S,
                KeyEvent.VK_A,
                KeyEvent.VK_D,
                KeyEvent.VK_SPACE);
        PlayerMovementController playerMovementController = new PlayerMovementController(
                game.getInputManager(),
                playerControls);
        AnimatedSprite playerSprite = new AnimatedSprite(
                100,
                new Sprite[] {
                        new Sprite("../assets/big_demon_idle_anim_f0.png"),
                        new Sprite("../assets/big_demon_idle_anim_f1.png"),
                        new Sprite("../assets/big_demon_idle_anim_f2.png"),
                        new Sprite("../assets/big_demon_idle_anim_f3.png") },
                new Sprite[] {});
        Creature player = new Creature(this, playerMovementController);
        player.setSprite(playerSprite);
        this.creatures.add(player);

        this.camera.setFollowing(player);
        this.camera.setZoom(50);
    }

    @Override
    public void update() {
        for (int i = 0; i < creatures.size(); i++) {
            creatures.get(i).update();
        }
        // zombieSpawner.update();
        camera.update();
    }

    public Game getGame() {
        return game;
    }

    public Tilemap getTilemap() {
        return tilemap;
    }

    public Camera getCamera() {
        return camera;
    }

    public ArrayList<Creature> getCreatures() {
        return creatures;
    }
}
