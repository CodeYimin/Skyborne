package scenes;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import core.Game;
import core.Updatable;
import core.UpdateInfo;
import entities.Entity;
import entities.GameObject;
import entities.Player;
import graphics.Camera;
import input.PlayerControls;
import util.Size;
import util.Vector;
import world.TileMap;

public class Level implements Updatable {
    private Game game;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private TileMap tileMap;
    private Camera camera;

    public Level(Game game) {
        this.game = game;

        tileMap = new TileMap(new int[][] {
                { 0, 1 },
                { 1, 1 }
        });
        addGameObject(tileMap);

        Player player = new Player(this, game.getInputManager());
        player.setControls(new PlayerControls(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D));
        player.setPosition(new Vector(0, 2));
        addGameObject(player);

        camera = new Camera(game.getWindow().getGraphicsPanel());
        camera.setFollowing(player);
        camera.setZoom(50);
        camera.addRenderable(tileMap);
        camera.addRenderable(player);
        addGameObject(camera);
    }

    @Override
    public void update(UpdateInfo updateInfo) {
        for (GameObject updatable : gameObjects) {
            updatable.update(updateInfo);
        }
    }

    public ArrayList<Integer> getCollidingTiles(Entity entity) {
        return tileMap.getCollidingTiles(entity);
    }

    public ArrayList<Integer> getCollidingTiles(Vector position, Size size) {
        return tileMap.getCollidingTiles(position, size);
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
