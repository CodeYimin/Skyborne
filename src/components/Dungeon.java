package components;

import java.util.ArrayList;

import core.GameObject;
import structures.IntVector;
import util.ObjectCreator;

public class Dungeon extends Component {
    public static final int WIDTH = 5;
    public static final int HEIGHT = 5;
    public static final int MIN_ROOMS = 5;
    public static final int MAX_ROOMS = 10;

    private GameObject[][] rooms;
    private int roomsGenerated = 0;

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

    private boolean generate(int x, int y) {
        if (roomsGenerated >= MAX_ROOMS) {
            return false;
        }

        if (roomsGenerated == 0) {
            createRoom(x, y, "../data/room0.txt", 0);
        } else if (Math.random() < 0.5) {
            createRoom(x, y, "../data/room1.txt", 10);
        } else {
            createRoom(x, y, "../data/room2.txt", 10);
        }
        roomsGenerated++;

        if (roomsGenerated < MIN_ROOMS) {
            generateRandomDirection(x, y);
        }
        if (roomsGenerated > 1 && Math.random() < 0.5) {
            generateRandomDirection(x, y);
        }

        return true;
    }

    private void generateRandomDirection(int x, int y) {
        ArrayList<IntVector> availableDirections = getAvailableDirections(x, y);
        if (availableDirections.size() == 0) {
            return;
        }
        int randomIndex = (int) (Math.random() * availableDirections.size());
        IntVector direction = availableDirections.get(randomIndex);
        if (generate(x + direction.getX(), y + direction.getY())) {
            connectRooms(x, y, x + direction.getX(), y + direction.getY());
        }
    }

    private ArrayList<IntVector> getAvailableDirections(int x, int y) {
        ArrayList<IntVector> availableDirections = new ArrayList<>();
        if (x > 0 && getRoom(x - 1, y) == null) {
            availableDirections.add(new IntVector(-1, 0));
        }
        if (x < WIDTH - 1 && getRoom(x + 1, y) == null) {
            availableDirections.add(new IntVector(1, 0));
        }
        if (y > 0 && getRoom(x, y - 1) == null) {
            availableDirections.add(new IntVector(0, -1));
        }
        if (y < HEIGHT - 1 && getRoom(x, y + 1) == null) {
            availableDirections.add(new IntVector(0, 1));
        }
        return availableDirections;
    }

    private GameObject createRoom(int x, int y, String mapPath, int maxEnemies) {
        GameObject room;
        room = ObjectCreator.createRoom(new IntVector(x, y), mapPath, maxEnemies);
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

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }
}
