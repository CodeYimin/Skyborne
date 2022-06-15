package scenes;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import collision.CollisionManager;
import core.Game;
import core.Updatable;
import entities.Entity;
import entities.Player;
import entities.Zombie;
import graphics.Camera;
import input.PlayerControls;
import util.Vector;
import world.Tilemap;

public class World implements Updatable {
    private Game game;
    private Camera camera;
    private CollisionManager collisionManager;

    private ArrayList<Entity> entities = new ArrayList<>();
    private Tilemap tilemap;

    public World(Game game) {
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
        this.collisionManager = new CollisionManager(tilemap);

        Player player = new Player(this, game.getInputManager());
        player.setControls(new PlayerControls(
                KeyEvent.VK_W,
                KeyEvent.VK_S,
                KeyEvent.VK_A,
                KeyEvent.VK_D,
                KeyEvent.VK_F));
        player.setPosition(new Vector(0, 2));
        instantiateEntity(player);

        Zombie zombie = new Zombie(this);
        zombie.setPosition(new Vector(1, 5));
        instantiateEntity(zombie);

        Zombie zombie2 = new Zombie(this);
        zombie2.setPosition(new Vector(1, 6));
        instantiateEntity(zombie2);

        Zombie zombie3 = new Zombie(this);
        zombie3.setPosition(new Vector(1, 7));
        instantiateEntity(zombie3);

        this.camera.setFollowing(player);
        this.camera.setZoom(50);
    }

    @Override
    public void update() {
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).update();
        }
        collisionManager.update();
        camera.update();
    }

    public void instantiateEntity(Entity entity) {
        entities.add(entity);
        camera.addRenderable(entity);
        collisionManager.addEntity(entity);
    }

    public boolean destroyEntity(Entity entity) {
        if (entities.remove(entity) && camera.removeRenderable(entity) && collisionManager.removeEntity(entity)) {
            return true;
        } else {
            return false;
        }
    }

    public Tilemap getTilemap() {
        return tilemap;
    }

    public Camera getCamera() {
        return camera;
    }

    public CollisionManager getCollisionManager() {
        return collisionManager;
    }
}
