package components;

import java.util.ArrayList;

import core.GameObject;
import structures.Directions;
import structures.IntVector;
import structures.Tile;
import structures.Vector;
import util.Const;
import util.ObjectCreator;

public class Room extends Tilemap {
    private ArrayList<Hallway> hallways = new ArrayList<>();

    private int floorMaterial;
    private int wallMaterial;
    private int doorMaterial;

    private boolean discovered;
    private boolean entered;
    private boolean cleared;

    private Tile[][] floorLayer;
    private Tile[][] contentLayer;
    private Tile[][] doorLayer;
    private Tile[][] wallLayer;
    private int maxEnemies;

    public Room(String mapPath, int floorMaterial, int wallMaterial, int doorMaterial, int maxEnemies) {
        super(mapPath);
        this.contentLayer = getLayer(0);

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
        int minX = -getWidth() / 2 + 2;
        int maxX = getWidth() / 2 - 2;
        int minY = -getHeight() / 2 + 2;
        int maxY = getHeight() / 2 - 2;

        for (int i = 0; i < maxEnemies; i++) {
            int randomLocalX = (int) (Math.random() * (maxX - minX) + minX);
            int randomLocalY = (int) (Math.random() * (maxY - minY) + minY);
            IntVector newEnemyLocalPosition = new IntVector(randomLocalX, randomLocalY);
            Vector newEnemyWorldPosition = getGameObject().getTransform().getPosition().add(newEnemyLocalPosition.toVector());

            if (Math.random() < 0.5 && !isSolidAt(newEnemyWorldPosition)) {
                GameObject player = getGameObject().getScene().getGameObject(Player.class);

                // Create enemy
                GameObject enemy = ObjectCreator.createEnemy(player, Vector.ZERO, Vector.ONE, 20, "../assets/masked_orc_idle_anim_f0.png");
                enemy.getTransform().setLocalPosition(newEnemyLocalPosition.toVector());
                enemy.setParent(getGameObject());
                enemy.getTransform().setRotation(0);
                getGameObject().getScene().addGameObject(enemy);

                // Create enemy weapon
                GameObject enemyWeapon = ObjectCreator.createEnemyWeapon(
                        enemy, player,
                        Vector.ONE.multiply(0.8),
                        "../assets/flask_green.png",
                        1, 1500, 3);
                getGameObject().getScene().addGameObject(enemyWeapon);
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
        super.update(deltaTime);

        if (!entered) {
            for (GameObject bullet : getGameObjectsInside(Bullet.class)) {
                bullet.destroy();
            }
        }

        // Player is in the room
        if (getGameObjectsInside(Player.class, contentLayer).size() > 0) {
            // Player first time entering room
            if (!entered) {
                generateDoors();
                unfreezeObjects();

                // This room is discovered and entered
                entered = true;
                discovered = true;

                // Connecting hallways/rooms are discovered
                for (Hallway hallway : getHallways()) {
                    hallway.setDiscovered(true);
                    Room otherRoom = hallway.getOtherRoom(this);
                    otherRoom.setDiscovered(true);
                }
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
        if (wallLayer != null) {
            return;
        }

        Directions hallwayDirections = getHallwayDirections();

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
                boolean isTopDoor = isTopBorder && isHorizontalDoorColumn && hallwayDirections.hasUp();
                boolean isBottomDoor = isBottomBorder && isHorizontalDoorColumn && hallwayDirections.hasDown();
                boolean isLeftDoor = isLeftBorder && isVerticalDoorRow && hallwayDirections.hasLeft();
                boolean isRightDoor = isRightBorder && isVerticalDoorRow && hallwayDirections.hasRight();
                boolean isDoor = isTopDoor || isBottomDoor || isLeftDoor || isRightDoor;
                if (isBorder && !isDoor) {
                    tiles[x][y] = new Tile(wallMaterial);
                } else {
                    tiles[x][y] = new Tile(0);
                }
            }
        }
        addLayer(tiles);
        wallLayer = tiles;
    }

    public void clearWalls() {
        if (wallLayer != null) {
            removeLayer(wallLayer);
            wallLayer = null;
        }
    }

    private void generateDoors() {
        if (doorLayer != null) {
            return;
        }

        Directions hallwayDirections = getHallwayDirections();

        Tile[][] tiles = new Tile[getWidth()][getHeight()];
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                boolean isTopBorder = y == getHeight() - 1;
                boolean isBottomBorder = y == 0;
                boolean isLeftBorder = x == 0;
                boolean isRightBorder = x == getWidth() - 1;
                boolean isHorizontalDoorColumn = x * 2 + 2 > getWidth() - Const.DUNGEON_HALLWAY_WIDTH && x * 2 + 2 <= getWidth() + Const.DUNGEON_HALLWAY_WIDTH;
                boolean isVerticalDoorRow = y * 2 + 2 > getHeight() - Const.DUNGEON_HALLWAY_WIDTH && y * 2 + 2 <= getHeight() + Const.DUNGEON_HALLWAY_WIDTH;
                boolean isTopDoor = isTopBorder && isHorizontalDoorColumn && hallwayDirections.hasUp();
                boolean isBottomDoor = isBottomBorder && isHorizontalDoorColumn && hallwayDirections.hasDown();
                boolean isLeftDoor = isLeftBorder && isVerticalDoorRow && hallwayDirections.hasLeft();
                boolean isRightDoor = isRightBorder && isVerticalDoorRow && hallwayDirections.hasRight();
                boolean isDoor = isTopDoor || isBottomDoor || isLeftDoor || isRightDoor;
                if (isDoor) {
                    tiles[x][y] = new Tile(doorMaterial);
                } else {
                    tiles[x][y] = new Tile(0);
                }
            }
        }
        addLayer(tiles);
        doorLayer = tiles;
    }

    public void clearDoors() {
        if (doorLayer != null) {
            removeLayer(doorLayer);
            doorLayer = null;
        }
    }

    public void addHallway(Hallway hallway) {
        hallways.add(hallway);

        if (wallLayer != null) {
            clearWalls();
            generateWalls();
        }

        if (doorLayer != null) {
            clearDoors();
            generateDoors();
        }
    }

    public ArrayList<Hallway> getHallways() {
        return hallways;
    }

    public Directions getHallwayDirections() {
        Directions hallwayDirections = new Directions();
        for (Hallway hallway : hallways) {
            Vector myPosition = getGameObject().getTransform().getPosition();
            Vector otherRoomPosition = hallway.getOtherRoomPosition(this);
            if (hallway.getDirection() == Hallway.HORIZONTAL) {
                if (myPosition.getX() < otherRoomPosition.getX()) {
                    hallwayDirections.setRight(true);
                } else {
                    hallwayDirections.setLeft(true);
                }
            } else {
                if (myPosition.getY() < otherRoomPosition.getY()) {
                    hallwayDirections.setUp(true);
                } else {
                    hallwayDirections.setDown(true);
                }
            }
        }
        return hallwayDirections;
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
