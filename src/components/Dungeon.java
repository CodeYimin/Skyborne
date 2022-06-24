package components;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import core.GameObject;
import structures.IntVector;
import util.ArrayUtils;
import util.Const;
import util.ObjectCreator;

public class Dungeon extends Component {
    public static final int WIDTH = Const.DUNGEON_WIDTH;
    public static final int HEIGHT = Const.DUNGEON_HEIGHT;
    public static final int MIN_ROOMS = Const.DUNGEON_MIN_ROOMS;
    public static final int MAX_ROOMS = Const.DUNGEON_MAX_ROOMS;

    private GameObject player;

    private IntVector startingPosition;
    private int numRooms;
    private GameObject[][] rooms;
    private int level = 1;

    public Dungeon(GameObject player) {
        this.player = player;
    }

    @Override
    public void start() {
        generate();
    }

    @Override
    public void update(double deltaTime) {
        int cleared = 0;

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                GameObject room = rooms[x][y];
                if (room != null && room.getComponent(Room.class).isCleared()) {
                    cleared++;
                }
            }
        }

        if (cleared == numRooms) {
            regenerate();
            level++;
        }
    }

    private void generate() {
        this.rooms = new GameObject[WIDTH][HEIGHT];
        this.numRooms = 0;
        this.startingPosition = getRandomPosition();

        // Queue for positions to branch new rooms from
        Queue<IntVector> queue = new LinkedList<>();

        // Create the first room with no enemies
        createRoom(startingPosition, "../data/room0.txt", 0);
        queue.add(startingPosition);

        // Generate rooms
        while (numRooms < MAX_ROOMS && !queue.isEmpty()) {
            // This position would already have a room generated, the code now creates new
            // rooms around this room
            IntVector position = queue.remove();

            String[] mapPaths = new String[] { "../data/room1.txt", "../data/room2.txt" };

            // Guaranteed branch out one room if min room requirement not hit
            if (numRooms < MIN_ROOMS) {
                IntVector newRoomPosition = createRoomRandomDirection(position, ArrayUtils.getRandom(mapPaths), 10);
                if (newRoomPosition != null) {
                    queue.add(newRoomPosition);
                }
            }

            // Chance to branch out 0-2 new rooms from this position.
            // First room purposely has only one branch
            if (!position.equals(startingPosition)) {
                for (int i = 0; i < 2; i++) {
                    if (Math.random() < 0.25) {
                        IntVector newRoomPosition = createRoomRandomDirection(position, ArrayUtils.getRandom(mapPaths), 10);
                        if (newRoomPosition != null) {
                            queue.add(newRoomPosition);
                        }
                    }
                }
            }
        }

        player.getTransform().setPosition(getRoom(startingPosition).getTransform().getPosition());
    }

    private void regenerate() {
        for (GameObject child : getGameObject().getChildren()) {
            child.destroy();
        }
        generate();
    }

    private IntVector getRandomPosition() {
        int x = (int) (Math.random() * WIDTH);
        int y = (int) (Math.random() * HEIGHT);
        return new IntVector(x, y);
    }

    private IntVector createRoomRandomDirection(IntVector position, String mapPath, int maxEnemies) {
        IntVector randomAvailableDirection = getRandomAvailableDirection(position);
        if (randomAvailableDirection == null) {
            return null;
        }

        IntVector randomAvailablePosition = position.add(randomAvailableDirection);
        createRoom(randomAvailablePosition, mapPath, maxEnemies);
        connectRooms(position, randomAvailablePosition);
        return randomAvailablePosition;
    }

    private ArrayList<IntVector> getAvailableDirections(int x, int y) {
        ArrayList<IntVector> availableDirections = new ArrayList<>();
        if (x > 0 && getRoom(x - 1, y) == null) {
            availableDirections.add(IntVector.LEFT);
        }
        if (x < WIDTH - 1 && getRoom(x + 1, y) == null) {
            availableDirections.add(IntVector.RIGHT);
        }
        if (y > 0 && getRoom(x, y - 1) == null) {
            availableDirections.add(IntVector.DOWN);
        }
        if (y < HEIGHT - 1 && getRoom(x, y + 1) == null) {
            availableDirections.add(IntVector.UP);
        }
        return availableDirections;
    }

    private ArrayList<IntVector> getAvailableDirections(IntVector position) {
        return getAvailableDirections(position.getX(), position.getY());
    }

    private IntVector getRandomAvailableDirection(int x, int y) {
        ArrayList<IntVector> availableDirections = getAvailableDirections(x, y);
        if (availableDirections.size() == 0) {
            return null;
        }

        int randomIndex = (int) (Math.random() * availableDirections.size());
        IntVector randomDirection = availableDirections.get(randomIndex);
        return randomDirection;
    }

    private IntVector getRandomAvailableDirection(IntVector position) {
        return getRandomAvailableDirection(position.getX(), position.getY());
    }

    private ArrayList<IntVector> getOccupiedDirections(int x, int y) {
        ArrayList<IntVector> occupiedDirections = new ArrayList<>();
        if (x > 0 && getRoom(x - 1, y) != null) {
            occupiedDirections.add(new IntVector(-1, 0));
        }
        if (x < WIDTH - 1 && getRoom(x + 1, y) != null) {
            occupiedDirections.add(new IntVector(1, 0));
        }
        if (y > 0 && getRoom(x, y - 1) != null) {
            occupiedDirections.add(new IntVector(0, -1));
        }
        if (y < HEIGHT - 1 && getRoom(x, y + 1) != null) {
            occupiedDirections.add(new IntVector(0, 1));
        }
        return occupiedDirections;
    }

    private ArrayList<IntVector> getOccupiedDirections(IntVector position) {
        return getOccupiedDirections(position.getX(), position.getY());
    }

    private void createRoom(IntVector position, String mapPath, int maxEnemies) {
        GameObject room = ObjectCreator.createRoom(position, mapPath, maxEnemies);
        setRoom(position, room);
        numRooms++;

        // Automatically connect hallway to all surrounding rooms unless the surrounding
        // room is the starting room and starting room already has one hallway
        for (IntVector direction : getOccupiedDirections(position)) {
            IntVector otherPosition = position.add(direction);
            Room otherRoomComponent = getRoom(otherPosition).getComponent(Room.class);
            if (!(otherPosition.equals(startingPosition) && otherRoomComponent.getHallways().size() > 0)) {
                connectRooms(position, otherPosition);
            }
        }

        // Instantiate the room object to the scene
        getGameObject().getScene().addGameObject(room, 0);
        room.setParent(getGameObject());
    }

    private void connectRooms(IntVector position1, IntVector position2) {
        Room room1 = getRoom(position1).getComponent(Room.class);
        Room room2 = getRoom(position2).getComponent(Room.class);

        // Create and instantiate hallway
        GameObject hallway = ObjectCreator.createHallway(room1, room2);
        hallway.setParent(getGameObject());
        getGameObject().getScene().addGameObject(hallway, 0);
    }

    public void addRoom(int x, int y, GameObject room) {
        rooms[x][y] = room;
    }

    public void addRoom(IntVector position, GameObject room) {
        rooms[position.getX()][position.getY()] = room;
    }

    public GameObject getRoom(int x, int y) {
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) {
            return null;
        }

        return rooms[x][y];
    }

    public GameObject getRoom(IntVector position) {
        return getRoom(position.getX(), position.getY());
    }

    public void setRoom(int x, int y, GameObject room) {
        rooms[x][y] = room;
    }

    public void setRoom(IntVector position, GameObject room) {
        rooms[position.getX()][position.getY()] = room;
    }

    public GameObject[][] getRooms() {
        return rooms;
    }

    public ArrayList<GameObject> getHallways() {
        ArrayList<GameObject> hallways = new ArrayList<>();
        for (GameObject child : getGameObject().getChildren()) {
            Hallway hallway = child.getComponent(Hallway.class);
            if (hallway != null) {
                hallways.add(child);
            }
        }
        return hallways;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getLevel() {
        return level;
    }
}
