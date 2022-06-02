package scenes;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import collision.CollisionManager;
import core.Game;
import core.Updatable;
import entities.Entity;
import entities.Player;
import graphics.Camera;
import input.PlayerControls;
import util.Vector;
import world.Tilemap;

public class World implements Updatable {
    private Game game;
    private Camera camera;
    private CollisionManager collisionManager = new CollisionManager();

    private ArrayList<Entity> entities = new ArrayList<>();
    private Tilemap tilemap;

    public World(Game game) {
        this.game = game;
        this.camera = new Camera(game.getWindow().getGraphicsPanel());

        this.tilemap = new Tilemap(new int[][] {
                { 1, 1, 0 },
                { 0, 1, 1 },
                { 1, 1, 0 },
                { 0, 1, 0 }
        });
        this.collisionManager.setTilemap(tilemap);
        this.camera.addRenderable(tilemap);

        Player player = new Player(this, game.getInputManager());
        player.setControls(new PlayerControls(
                KeyEvent.VK_W,
                KeyEvent.VK_S,
                KeyEvent.VK_A,
                KeyEvent.VK_D,
                KeyEvent.VK_F));
        player.setPosition(new Vector(0, 2));
        instantiateEntity(player);

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

    public CollisionManager getCollisionManager() {
        return collisionManager;
    }

    public Tilemap getTilemap() {
        return tilemap;
    }

    public Camera getCamera() {
        return camera;
    }
}
