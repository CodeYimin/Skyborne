package components;

import java.util.ArrayList;

import core.GameObject;
import structures.Tile;
import structures.Vector;
import util.Const;
import util.ObjectCreator;

public class Room extends Tilemap {
    private int floorMaterial;
    private int wallMaterial;
    private int doorMaterial;
    private boolean discovered;
    private boolean cleared;
    private int doorLayer;

    public Room(String mapPath, int floorMaterial, int wallMaterial, int doorMaterial) {
        super(mapPath);

        this.floorMaterial = floorMaterial;
        this.wallMaterial = wallMaterial;
        this.doorMaterial = doorMaterial;
    }

    @Override
    public void start() {
        generateFloors();
        generateWalls();
        spawnEnemies();
        freezeObjects();
    }

    public void spawnEnemies() {
        for (int x = -getWidth() / 2 + 1; x < getWidth() / 2 - 1; x++) {
            for (int y = -getHeight() / 2 + 1; y < getHeight() / 2 - 1; y++) {
                if (Math.random() < 0.05) {
                    Transform transform = getGameObject().getTransform();
                    GameObject enemy = ObjectCreator.createEnemy(getGameObject().getScene().getGameObject(Player.class),
                            transform.getPosition().add(x, y), Vector.ONE,
                            20, "../assets/masked_orc_idle_anim_f0.png");
                    enemy.getTransform().setLocalPosition(new Vector(x, y));
                    enemy.setParent(getGameObject());
                    enemy.getTransform().setRotation(Math.PI / 2);
                    getGameObject().getScene().addGameObject(enemy);

                    GameObject enemyWeapon = ObjectCreator.createEnemyWeapon(
                            enemy, getGameObject().getScene().getGameObject(Player.class),
                            Vector.ONE.multiply(0.8),
                            "../assets/flask_green.png",
                            1, 500, 5);
                    getGameObject().getScene().addGameObject(enemyWeapon);
                }
            }
        }
    }

    private void freezeObjects() {
        for (GameObject gameObject : getGameObject().getChildren()) {
            gameObject.disable();
        }
    }

    private void unfreezeObjects() {
        for (GameObject gameObject : getGameObject().getChildren()) {
            gameObject.enable();
        }
    }

    @Override
    public void update(double deltaTime) {
        if (!discovered) {
            for (GameObject bullet : getGameObjectsInRoom(Bullet.class)) {
                bullet.destroy();
            }
        }

        if (!cleared && getGameObjectsInRoom(Player.class).size() > 0) {
            if (!discovered) {
                discovered = true;
                generateDoors();
                unfreezeObjects();
            }

            ArrayList<Enemy> enemyComponents = getGameObject().getScene().getComponents(Enemy.class);
            int enemiesInRoom = 0;
            for (Enemy enemyComponent : enemyComponents) {
                if (isInRoom(enemyComponent.getGameObject())) {
                    enemiesInRoom++;
                }
            }
            if (enemiesInRoom == 0) {
                clearDoors();
                cleared = true;
            }
        }
    }

    public ArrayList<GameObject> getGameObjectsInRoom(Class<? extends Component> componentClass) {
        ArrayList<GameObject> objects = new ArrayList<>();
        for (GameObject object : getGameObject().getScene().getGameObjects(componentClass)) {
            if (isInRoom(object)) {
                objects.add(object);
            }
        }
        return objects;
    }

    public boolean isInRoom(GameObject gameObject) {
        return isInRoom(gameObject.getTransform());
    }

    public boolean isInRoom(Transform transform) {
        Vector position = transform.getPosition();
        Vector scale = transform.getScale();
        Vector localPosition = getLocalPosition(position);
        return localPosition.getX() >= 1 + scale.getX() / 2
                && localPosition.getX() < getWidth() - 1 - scale.getX() / 2
                && localPosition.getY() >= 1 + scale.getY() / 2
                && localPosition.getY() < getHeight() - 1 - scale.getY() / 2;
    }

    private void generateFloors() {
        Tile[][] tiles = new Tile[getWidth() + 2][getHeight() + 2];
        for (int x = 0; x < getWidth() + 2; x++) {
            for (int y = 0; y < getHeight() + 2; y++) {
                tiles[x][y] = new Tile(floorMaterial);
            }
        }
        addLayer(0, tiles);
    }

    private void generateWalls() {
        Tile[][] tiles = new Tile[getWidth()][getHeight()];
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                boolean isBorder = x == 0 || x == getWidth() - 1 || y == 0 || y == getHeight() - 1;
                boolean isHallway = (x * 2 + 2 > getWidth() - Const.HALLWAY_WIDTH && x * 2 + 2 <= getWidth() + Const.HALLWAY_WIDTH)
                        || (y * 2 + 2 > getHeight() - Const.HALLWAY_WIDTH && y * 2 + 2 <= getHeight() + Const.HALLWAY_WIDTH);
                if (isBorder && !isHallway) {
                    tiles[x][y] = new Tile(wallMaterial);
                } else {
                    tiles[x][y] = new Tile(0);
                }
            }
        }
        addLayer(tiles);
    }

    private void generateDoors() {
        Tile[][] tiles = new Tile[getWidth()][getHeight()];
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                boolean isBorder = x == 0 || x == getWidth() - 1 || y == 0 || y == getHeight() - 1;
                boolean isHallway = (x * 2 + 2 > getWidth() - Const.HALLWAY_WIDTH && x * 2 + 2 <= getWidth() + Const.HALLWAY_WIDTH)
                        || (y * 2 + 2 > getHeight() - Const.HALLWAY_WIDTH && y * 2 + 2 <= getHeight() + Const.HALLWAY_WIDTH);
                if (isBorder && isHallway) {
                    tiles[x][y] = new Tile(doorMaterial);
                } else {
                    tiles[x][y] = new Tile(0);
                }
            }
        }
        addLayer(tiles);
        doorLayer = getLayers().size() - 1;
    }

    public void clearDoors() {
        removeLayer(doorLayer);
    }
}
