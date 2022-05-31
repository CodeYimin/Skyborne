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
        // player.setControls(new PlayerControls(KeyEvent.VK_W, KeyEvent.VK_S,
        // KeyEvent.VK_A, KeyEvent.VK_D));
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

        // // Get collidable objects
        // ArrayList<Collidable<?>> collidables = new ArrayList<>();
        // for (GameObject gameObject : gameObjects) {
        // if (gameObject instanceof Collidable<?>) {
        // collidables.add((Collidable<?>) gameObject);
        // }
        // }

        // // Check collision
        // for (int i = 0; i < collidables.size(); i++) {
        // for (int j = i + 1; j < collidables.size(); j++) {
        // Collidable<?> collidable1 = collidables.get(i);
        // Collidable<?> collidable2 = collidables.get(j);

        // Collider<?> collider1 = collidable1.getCollider();
        // Collider<?> collider2 = collidable2.getCollider();

        // CollisionInfo collisionInfo1 = collider1.getCollisionInfo(collider2);
        // CollisionInfo collisionInfo2 = collider2.getCollisionInfo(collider1);

        // if (collisionInfo1 != null && collisionInfo2 != null) {
        // collidable1.onCollision(new Collision(collisionInfo1, collisionInfo2));
        // collidable2.onCollision(new Collision(collisionInfo2, collisionInfo1));
        // }
        // }
        // }
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }
}
