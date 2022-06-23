package components;

import core.GameObject;
import structures.IntVector;
import structures.Tile;
import structures.Vector;

public class Hallway extends Tilemap {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    private Room room1;
    private Room room2;
    private int floorMaterial;
    private int wallMaterial;
    private int hallwayWidth;

    private boolean discovered;
    private boolean entered;
    private int direction;

    public Hallway(Room room1, Room room2, int floorMaterial, int wallMaterial, int hallwayWidth) {
        this.room1 = room1;
        this.room2 = room2;
        this.floorMaterial = floorMaterial;
        this.wallMaterial = wallMaterial;
        this.hallwayWidth = hallwayWidth;

        this.discovered = false;
        this.entered = false;
    }

    @Override
    public void start() {
        super.start();

        GameObject roomObject1 = room1.getGameObject();
        GameObject roomObject2 = room2.getGameObject();
        if (roomObject1 == null || roomObject2 == null) {
            return;
        }

        Transform myTransform = getGameObject().getTransform();
        Transform transform1 = roomObject1.getTransform();
        Transform transform2 = roomObject2.getTransform();
        Vector position1 = transform1.getPosition();
        Vector position2 = transform2.getPosition();
        Vector size1 = room1.getSize().toVector();
        Vector size2 = room2.getSize().toVector();

        if (position1.getY() == position2.getY()) {
            direction = HORIZONTAL;
        } else {
            direction = VERTICAL;
        }

        Vector hallwayPosition;
        IntVector hallwaySize;
        if (direction == HORIZONTAL) {
            // right edge of left room
            double x1 = Math.min(position1.getX() + size1.getX() / 2, position2.getX() + size2.getX() / 2);
            // left edge of right room
            double x2 = Math.max(position1.getX() - size1.getX() / 2, position2.getX() - size2.getX() / 2);
            hallwayPosition = new Vector((x1 + x2) / 2, position1.getY());
            hallwaySize = new IntVector(x2 - x1, hallwayWidth + 2);
        } else {
            // top edge of bottom room
            double y1 = Math.min(position1.getY() + size1.getY() / 2, position2.getY() + size2.getY() / 2);
            // bottom edge of top room
            double y2 = Math.max(position1.getY() - size1.getY() / 2, position2.getY() - size2.getY() / 2);
            hallwayPosition = new Vector(position1.getX(), (y1 + y2) / 2);
            hallwaySize = new IntVector(hallwayWidth + 2, y2 - y1);
        }

        Tile[][] floorTiles = new Tile[hallwaySize.getX()][hallwaySize.getY()];
        for (int x = 0; x < floorTiles.length; x++) {
            for (int y = 0; y < floorTiles[x].length; y++) {
                floorTiles[x][y] = new Tile(floorMaterial);
            }
        }

        Tile[][] wallTiles = new Tile[hallwaySize.getX()][hallwaySize.getY()];
        if (direction == HORIZONTAL) {
            for (int x = 0; x < hallwaySize.getX(); x++) {
                wallTiles[x][0] = new Tile(wallMaterial);
                wallTiles[x][wallTiles[0].length - 1] = new Tile(wallMaterial);
            }
        } else {
            for (int y = 0; y < hallwaySize.getY(); y++) {
                wallTiles[0][y] = new Tile(wallMaterial);
                wallTiles[wallTiles.length - 1][y] = new Tile(wallMaterial);
            }
        }

        myTransform.setPosition(hallwayPosition);
        addLayer(floorTiles);
        addLayer(wallTiles);
        room1.addHallway(this);
        room2.addHallway(this);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        Player player = getGameObject().getScene().getComponent(Player.class);
        if (player != null && isInside(player.getGameObject())) {
            discovered = true;
            entered = true;
        }
    }

    public int getDirection() {
        return direction;
    }

    public Room getOtherRoom(Room room) {
        if (room == room1) {
            return room2;
        } else if (room == room2) {
            return room1;
        } else {
            return null;
        }
    }

    public Room getRoom1() {
        return room1;
    }

    public Room getRoom2() {
        return room2;
    }

    public GameObject getOtherRoomObject(Room room) {
        return getOtherRoom(room).getGameObject();
    }

    public GameObject getRoom1Object() {
        return room1.getGameObject();
    }

    public GameObject getRoom2Object() {
        return room2.getGameObject();
    }

    public Transform getOtherRoomTransform(Room room) {
        return getOtherRoomObject(room).getTransform();
    }

    public Transform getRoom1Transform() {
        return room1.getGameObject().getTransform();
    }

    public Transform getRoom2Transform() {
        return room2.getGameObject().getTransform();
    }

    public Vector getOtherRoomPosition(Room room) {
        return getOtherRoomTransform(room).getPosition();
    }

    public Vector getRoom1Position() {
        return room1.getGameObject().getTransform().getPosition();
    }

    public Vector getRoom2Position() {
        return room2.getGameObject().getTransform().getPosition();
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }

    public boolean isEntered() {
        return entered;
    }

    public void setEntered(boolean entered) {
        this.entered = entered;
    }
}
