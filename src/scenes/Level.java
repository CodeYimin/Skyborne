package scenes;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import core.Game;
import core.Updatable;
import entities.Entity;
import entities.GameObject;
import entities.Hitbox;
import entities.Player;
import graphics.Camera;
import input.PlayerControls;
import util.Vector;
import world.Tilemap;

public class Level implements Updatable {
    private Game game;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private Tilemap tilemap;
    private Camera camera;

    public Level(Game game) {
        this.game = game;

        tilemap = new Tilemap(new int[][] {
                { 1, 1, 0 },
                { 0, 1, 1 },
                { 1, 1, 0 },
                { 0, 1, 0 }
        });
        addGameObject(tilemap);

        Player player = new Player(this, game.getInputManager());
        player.setControls(new PlayerControls(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D));
        player.setPosition(new Vector(0, 2));
        addGameObject(player);

        camera = new Camera(game.getWindow().getGraphicsPanel());
        camera.setFollowing(player);
        camera.setZoom(50);
        camera.addRenderable(tilemap);
        camera.addRenderable(player);
        addGameObject(camera);
    }

    @Override
    public void update() {
        for (GameObject updatable : gameObjects) {
            updatable.update();
        }

        checkEntityCollision();
    }

    public void checkEntityCollision() {
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Entity) {
                Entity entity = (Entity) gameObject;

                ArrayList<Entity> collidingEntities = getCollidingEntities(entity);
                for (Entity collidingEntity : collidingEntities) {
                    entity.onCollision(collidingEntity);
                }

                ArrayList<Integer> collidingTiles = getCollidingTiles(entity);
                for (int collidingTile : collidingTiles) {
                    entity.onCollision(collidingTile);
                }
            }
        }
    }

    public ArrayList<Integer> getCollidingTiles(Entity entity) {
        return tilemap.getCollidingTiles(entity);
    }

    public ArrayList<Integer> getCollidingTiles(Hitbox boundingBox) {
        return tilemap.getCollidingTiles(boundingBox);
    }

    public int getTileAt(Vector position) {
        return tilemap.getTileAt(position);
    }

    public ArrayList<Entity> getCollidingEntities(Entity entity) {
        ArrayList<Entity> collidingEntities = new ArrayList<>();
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Entity && gameObject != entity) {
                Entity otherEntity = (Entity) gameObject;
                if (entity.collidesWith(otherEntity)) {
                    collidingEntities.add(otherEntity);
                }
            }
        }
        return collidingEntities;
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }
}
