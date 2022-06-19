package components;

import core.GameObject;
import structures.IntVector;
import util.ObjectCreator;

public class Dungeon extends Component {
    public static final int WIDTH = 5;
    public static final int HEIGHT = 5;

    private GameObject[][] rooms;

    public Dungeon() {
        rooms = new GameObject[WIDTH][HEIGHT];
    }

    @Override
    public void start() {
        generate(0, 0);

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (rooms[x][y] != null) {
                    getGameObject().getScene().addGameObject(rooms[x][y], 0);
                    rooms[x][y].setParent(getGameObject());
                }
            }
        }
    }

    private void generate(int x, int y) {
        double chance = 0.3;

        createRoom(x, y);
        if (getRoom(x - 1, y) != null) {
            connectRooms(x, y, x - 1, y);
        } else if (Math.random() < chance && x > 0) {
            generate(x - 1, y);
            connectRooms(x, y, x - 1, y);
        }
        if (getRoom(x + 1, y) != null) {
            connectRooms(x, y, x + 1, y);
        } else if (Math.random() < chance && x < WIDTH - 1) {
            generate(x + 1, y);
            connectRooms(x, y, x + 1, y);
        }
        if (getRoom(x, y - 1) != null) {
            connectRooms(x, y, x, y - 1);
        } else if (Math.random() < chance && y > 0) {
            generate(x, y - 1);
            connectRooms(x, y, x, y - 1);
        }
        if (getRoom(x, y + 1) != null) {
            connectRooms(x, y, x, y + 1);
        } else if (Math.random() < chance && y < HEIGHT - 1) {
            generate(x, y + 1);
            connectRooms(x, y, x, y + 1);
        }
    }

    private GameObject createRoom(int x, int y) {
        GameObject room = ObjectCreator.createRoom(new IntVector(x, y), "../data/room1.txt");
        rooms[x][y] = room;
        return room;
    }

    private void connectRooms(int x1, int y1, int x2, int y2) {
        Room room1 = getRoom(x1, y1).getComponent(Room.class);
        Room room2 = getRoom(x2, y2).getComponent(Room.class);

        if (x1 > x2) {
            room1.getDoorDirections().setLeft(true);
            room2.getDoorDirections().setRight(true);
        } else if (x1 < x2) {
            room1.getDoorDirections().setRight(true);
            room2.getDoorDirections().setLeft(true);
        } else if (y1 > y2) {
            room1.getDoorDirections().setDown(true);
            room2.getDoorDirections().setUp(true);
        } else if (y1 < y2) {
            room1.getDoorDirections().setUp(true);
            room2.getDoorDirections().setDown(true);
        }

        GameObject hallway = ObjectCreator.createHallway(room1.getGameObject(), room2.getGameObject());
        getGameObject().getScene().addGameObject(hallway, 0);
    }

    public void addRoom(int x, int y, GameObject room) {
        rooms[x][y] = room;
    }

    public GameObject getRoom(int x, int y) {
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) {
            return null;
        }

        return rooms[x][y];
    }

    public GameObject[][] getRooms() {
        return rooms;
    }
}
