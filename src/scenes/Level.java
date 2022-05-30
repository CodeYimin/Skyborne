package scenes;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import core.Game;
import core.Updatable;
import core.UpdateInfo;
import entities.GameObject;
import entities.Player;
import graphics.Camera;
import input.VectorCompositeBinding;
import util.Vector;
import world.TileMap;

public class Level implements Updatable {
    private Game game;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();

    public Level(Game game) {
        this.game = game;

        VectorCompositeBinding movementControls = new VectorCompositeBinding(game.getInputManager(), new Vector(0, 0));
        movementControls.addBinding(KeyEvent.VK_W, new Vector(0, 1));
        movementControls.addBinding(KeyEvent.VK_S, new Vector(0, -1));
        movementControls.addBinding(KeyEvent.VK_A, new Vector(-1, 0));
        movementControls.addBinding(KeyEvent.VK_D, new Vector(1, 0));

        TileMap map = new TileMap(new int[][] {
                { 0, 1 },
                { 1, 1 }
        });
        addGameObject(map);

        Player player = new Player(movementControls);
        player.setTileMap(map);
        player.setPosition(new Vector(0, 2));
        addGameObject(player);

        Camera camera = new Camera(game.getWindow().getGraphicsPanel());
        camera.setFollowing(player);
        camera.setZoom(100);
        camera.addRenderable(map);
        camera.addRenderable(player);
        addGameObject(camera);
    }

    @Override
    public void update(UpdateInfo updateInfo) {
        for (GameObject updatable : gameObjects) {
            updatable.update(updateInfo);
        }
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }
}
