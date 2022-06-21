package components;

import java.util.ArrayList;

import core.GameObject;
import events.EventListener;
import events.EventManager;
import events.RoomEnterEvent;
import structures.Directions;
import structures.Tile;
import structures.Vector;
import util.Const;
import util.ObjectCreator;

public class Room extends Tilemap {
    private Directions doorDirections = new Directions();
    private int floorMaterial;
    private int wallMaterial;
    private int doorMaterial;
    private boolean discovered;
    private boolean entered;
    private boolean cleared;
    private int doorLayer;
    private int maxEnemies;
    private EventManager<RoomEnterEvent> roomEnterEventManager = new EventManager<>();

    public Room(String mapPath, int floorMaterial, int wallMaterial, int doorMaterial, int maxEnemies) {
        super(mapPath);

        this.floorMaterial = floorMaterial;
        this.wallMaterial = wallMaterial;
        this.doorMaterial = doorMaterial;
        this.maxEnemies = maxEnemies;

        generateFloors();
    }

    @Override
    public void start() {
        generateWalls();
        spawnEnemies();
        freezeObjects();
    }

    public void spawnEnemies() {
        for (int x = -getWidth() / 2 + 2; x < getWidth() / 2 - 2; x++) {
            for (int y = -getHeight() / 2 + 2; y < getHeight() / 2 - 2; y++) {
                Transform transform = getGameObject().getTransform();
                if (getEnemiesInRoom() < maxEnemies && Math.random() < 0.2 && !isSolidAt(transform.getPosition().add(x, y))) {
                    GameObject enemy = ObjectCreator.createEnemy(getGameObject().getScene().getGameObject(Player.class),
                            transform.getPosition().add(x, y), Vector.ONE,
                            20, "../assets/masked_orc_idle_anim_f0.png");
                    enemy.getTransform().setLocalPosition(new Vector(x, y));
                    enemy.setParent(getGameObject());
                    enemy.getTransform().setRotation(0);
                    getGameObject().getScene().addGameObject(enemy);

                    GameObject enemyWeapon = ObjectCreator.createEnemyWeapon(
                            enemy, getGameObject().getScene().getGameObject(Player.class),
                            Vector.ONE.multiply(0.8),
                            "../assets/flask_green.png",
                            1, 1500, 3);
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
        if (!entered) {
            for (GameObject bullet : getGameObjectsInRoom(Bullet.class)) {
                bullet.destroy();
            }
        }

        // Player is in the room
        if (getGameObjectsInRoom(Player.class).size() > 0) {
            // Player first time entering room
            if (!entered) {
                discovered = true;
                entered = true;
                generateDoors();
                unfreezeObjects();

                roomEnterEventManager.emit(new RoomEnterEvent(true));
            }

            // Cleared all enemies
            if (!cleared && getEnemiesInRoom() == 0) {
                clearDoors();
                cleared = true;
            }
        }
    }

    public int getEnemiesInRoom() {
        int enemiesInRoom = 0;
        for (GameObject child : getGameObject().getChildren()) {
            if (child.getComponent(Enemy.class) != null) {
                enemiesInRoom++;
            }
        }
        return enemiesInRoom;
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
        Transform transform = gameObject.getTransform();
        Vector position = transform.getPosition();
        Vector scale = transform.getScale();
        Vector localPosition = getLocalPosition(position);
        return localPosition.getX() - scale.getX() / 2 >= 1
                && localPosition.getX() + scale.getX() / 2 <= getWidth() - 1
                && localPosition.getY() - scale.getY() / 2 >= 1
                && localPosition.getY() + scale.getY() / 2 <= getHeight() - 1;
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
                boolean isTopBorder = y == getHeight() - 1;
                boolean isBottomBorder = y == 0;
                boolean isLeftBorder = x == 0;
                boolean isRightBorder = x == getWidth() - 1;
                boolean isBorder = isTopBorder || isBottomBorder || isLeftBorder || isRightBorder;
                boolean isHorizontalDoorColumn = x * 2 + 2 > getWidth() - Const.DUNGEON_HALLWAY_WIDTH && x * 2 + 2 <= getWidth() + Const.DUNGEON_HALLWAY_WIDTH;
                boolean isVerticalDoorRow = y * 2 + 2 > getHeight() - Const.DUNGEON_HALLWAY_WIDTH && y * 2 + 2 <= getHeight() + Const.DUNGEON_HALLWAY_WIDTH;
                boolean isTopDoor = isTopBorder && isHorizontalDoorColumn && doorDirections.hasUp();
                boolean isBottomDoor = isBottomBorder && isHorizontalDoorColumn && doorDirections.hasDown();
                boolean isLeftDoor = isLeftBorder && isVerticalDoorRow && doorDirections.hasLeft();
                boolean isRightDoor = isRightBorder && isVerticalDoorRow && doorDirections.hasRight();
                boolean isDoor = isTopDoor || isBottomDoor || isLeftDoor || isRightDoor;
                if (isBorder && !isDoor) {
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
                boolean isTopBorder = y == getHeight() - 1;
                boolean isBottomBorder = y == 0;
                boolean isLeftBorder = x == 0;
                boolean isRightBorder = x == getWidth() - 1;
                boolean isHorizontalDoorColumn = x * 2 + 2 > getWidth() - Const.DUNGEON_HALLWAY_WIDTH && x * 2 + 2 <= getWidth() + Const.DUNGEON_HALLWAY_WIDTH;
                boolean isVerticalDoorRow = y * 2 + 2 > getHeight() - Const.DUNGEON_HALLWAY_WIDTH && y * 2 + 2 <= getHeight() + Const.DUNGEON_HALLWAY_WIDTH;
                boolean isTopDoor = isTopBorder && isHorizontalDoorColumn && doorDirections.hasUp();
                boolean isBottomDoor = isBottomBorder && isHorizontalDoorColumn && doorDirections.hasDown();
                boolean isLeftDoor = isLeftBorder && isVerticalDoorRow && doorDirections.hasLeft();
                boolean isRightDoor = isRightBorder && isVerticalDoorRow && doorDirections.hasRight();
                boolean isDoor = isTopDoor || isBottomDoor || isLeftDoor || isRightDoor;
                if (isDoor) {
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

    public Directions getDoorDirections() {
        return doorDirections;
    }

    public void addRoomEnterListener(EventListener<RoomEnterEvent> listener) {
        roomEnterEventManager.addListener(listener);
    }

    public void removeRoomEnterListener(EventListener<RoomEnterEvent> listener) {
        roomEnterEventManager.removeListener(listener);
    }

    public boolean isCleared() {
        return cleared;
    }

    public boolean isEntered() {
        return entered;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }

    public int getMaxEnemies() {
        return maxEnemies;
    }

    public void setMaxEnemies(int maxEnemies) {
        this.maxEnemies = maxEnemies;
    }
}
