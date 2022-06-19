package components;

import java.util.ArrayList;

import core.GameObject;
import structures.Tile;
import structures.Vector;
import util.Const;

public class Room extends Component {
    private Tilemap tilemap;
    private int floorMaterial;
    private int wallMaterial;
    private int doorMaterial;
    private boolean discovered;
    private boolean cleared;
    private int doorLayer;

    public Room(Tilemap tilemap, int floorMaterial, int wallMaterial, int doorMaterial) {
        this.tilemap = tilemap;
        this.floorMaterial = floorMaterial;
        this.wallMaterial = wallMaterial;
        this.doorMaterial = doorMaterial;
    }

    @Override
    public void start() {
        generateFloors();
        generateWalls();

        ArrayList<Motion> motionComponents = getGameObject().getScene().getComponents(Motion.class);
        for (Motion motionComponent : motionComponents) {
            if (isInRoom(motionComponent.getGameObject())) {
                motionComponent.setState(Motion.FROZEN);
            }
        }
    }

    @Override
    public void update(double deltaTime) {
        if (!cleared && getPlayersInRoom().size() > 0) {
            if (!discovered) {
                discovered = true;
                generateDoors();

                ArrayList<Motion> motionComponents = getGameObject().getScene().getComponents(Motion.class);
                for (Motion motionComponent : motionComponents) {
                    if (isInRoom(motionComponent.getGameObject())) {
                        motionComponent.setState(Motion.IDLE);
                    }
                }
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

    public ArrayList<GameObject> getPlayersInRoom() {
        ArrayList<GameObject> players = new ArrayList<>();
        for (GameObject playerObject : getGameObject().getScene().getGameObjects(Player.class)) {
            if (isInRoom(playerObject)) {
                players.add(playerObject);
            }
        }
        return players;
    }

    public boolean isInRoom(GameObject gameObject) {
        return isInRoom(gameObject.getComponent(Transform.class));
    }

    public boolean isInRoom(Transform transform) {
        Vector position = transform.getPosition();
        Vector scale = transform.getScale();
        Vector localPosition = tilemap.getLocalPosition(position);
        return localPosition.getX() >= 1 + scale.getX() / 2
                && localPosition.getX() < tilemap.getWidth() - 1 - scale.getX() / 2
                && localPosition.getY() >= 1 + scale.getY() / 2
                && localPosition.getY() < tilemap.getHeight() - 1 - scale.getY() / 2;
    }

    private void generateFloors() {
        Tile[][] tiles = new Tile[tilemap.getWidth() + 2][tilemap.getHeight() + 2];
        for (int x = 0; x < tilemap.getWidth() + 2; x++) {
            for (int y = 0; y < tilemap.getHeight() + 2; y++) {
                tiles[x][y] = new Tile(floorMaterial);
            }
        }
        tilemap.addLayer(0, tiles);
    }

    private void generateWalls() {
        Tile[][] tiles = new Tile[tilemap.getWidth()][tilemap.getHeight()];
        for (int x = 0; x < tilemap.getWidth(); x++) {
            for (int y = 0; y < tilemap.getHeight(); y++) {
                boolean isBorder = x == 0 || x == tilemap.getWidth() - 1 || y == 0 || y == tilemap.getHeight() - 1;
                boolean isHallway = (x * 2 + 2 > tilemap.getWidth() - Const.HALLWAY_WIDTH && x * 2 + 2 <= tilemap.getWidth() + Const.HALLWAY_WIDTH)
                        || (y * 2 + 2 > tilemap.getHeight() - Const.HALLWAY_WIDTH && y * 2 + 2 <= tilemap.getHeight() + Const.HALLWAY_WIDTH);
                if (isBorder && !isHallway) {
                    tiles[x][y] = new Tile(wallMaterial);
                } else {
                    tiles[x][y] = new Tile(0);
                }
            }
        }
        tilemap.addLayer(tiles);
    }

    private void generateDoors() {
        Tile[][] tiles = new Tile[tilemap.getWidth()][tilemap.getHeight()];
        for (int x = 0; x < tilemap.getWidth(); x++) {
            for (int y = 0; y < tilemap.getHeight(); y++) {
                boolean isBorder = x == 0 || x == tilemap.getWidth() - 1 || y == 0 || y == tilemap.getHeight() - 1;
                boolean isHallway = (x * 2 + 2 > tilemap.getWidth() - Const.HALLWAY_WIDTH && x * 2 + 2 <= tilemap.getWidth() + Const.HALLWAY_WIDTH)
                        || (y * 2 + 2 > tilemap.getHeight() - Const.HALLWAY_WIDTH && y * 2 + 2 <= tilemap.getHeight() + Const.HALLWAY_WIDTH);
                if (isBorder && isHallway) {
                    tiles[x][y] = new Tile(doorMaterial);
                } else {
                    tiles[x][y] = new Tile(0);
                }
            }
        }
        tilemap.addLayer(tiles);
        doorLayer = tilemap.getLayers().size() - 1;
    }

    public void clearDoors() {
        tilemap.removeLayer(doorLayer);
    }
}
