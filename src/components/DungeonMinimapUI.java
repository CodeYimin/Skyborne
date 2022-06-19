package components;

import java.awt.Color;
import java.awt.Graphics;

import core.GameObject;
import core.GraphicsPanel;
import structures.Vector;
import util.Const;

public class DungeonMinimapUI extends UI {
    public static final int WIDTH = 200;
    public static final int HEIGHT = 200;
    public static final int MARGIN = 20;

    @Override
    public void draw(Graphics g) {
        Dungeon dungeon = getGameObject().getComponent(Dungeon.class);
        if (dungeon == null) {
            return;
        }

        GraphicsPanel graphicsPanel = getGameObject().getScene().getGame().getWindow().getGraphicsPanel();
        int screenWidth = graphicsPanel.getWidth();
        int screenHeight = graphicsPanel.getHeight();

        int mapWidth = dungeon.getWidth();
        int mapHeight = dungeon.getHeight();
        GameObject[][] rooms = dungeon.getRooms();

        g.setColor(Color.BLACK);
        g.fillRect(screenWidth - WIDTH - MARGIN - 3, MARGIN - 3, WIDTH + 6, HEIGHT + 6);
        g.setColor(Color.WHITE);
        g.fillRect(screenWidth - WIDTH - MARGIN, MARGIN, WIDTH, HEIGHT);

        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                if (rooms[x][y] == null) {
                    continue;
                }

                Transform transform = rooms[x][y].getTransform();
                Room room = rooms[x][y].getComponent(Room.class);
                if (room.isDiscovered()) {
                    Color color;
                    if (room.getGameObjectsInRoom(Player.class).size() > 0) {
                        color = Color.RED;
                    } else {
                        color = Color.GRAY;
                    }
                    drawRectangle(g, transform.getLocalPosition().add(Const.DISTANCE_BETWEEN_ROOMS / 2), new Vector(room.getWidth(), room.getHeight()), color);
                }
            }
        }

        Player player = getGameObject().getScene().getComponent(Player.class);
        if (player != null) {
            Transform playerTransform = player.getGameObject().getTransform();
            Vector playerPosition = playerTransform.getPosition();
            Transform dungeonTransform = dungeon.getGameObject().getTransform();
            Vector dungeonPosition = dungeonTransform.getPosition();

            Vector playerLocalPosition = playerPosition.subtract(dungeonPosition);
            drawRectangle(g, playerLocalPosition.add(Const.DISTANCE_BETWEEN_ROOMS / 2), Vector.ONE.multiply(5), Color.BLACK);
        }
    }

    private void drawRectangle(Graphics g, Vector position, Vector size, Color color) {
        Dungeon dungeon = getGameObject().getComponent(Dungeon.class);
        if (dungeon == null) {
            return;
        }

        GraphicsPanel graphicsPanel = getGameObject().getScene().getGame().getWindow().getGraphicsPanel();
        int screenWidth = graphicsPanel.getWidth();

        int mapWidth = dungeon.getWidth();

        int minimapTop = MARGIN;
        int minimapLeft = screenWidth - WIDTH - MARGIN;
        int minimapBottom = minimapTop + HEIGHT;

        double screenTileSize = (double) WIDTH / (mapWidth * Const.DISTANCE_BETWEEN_ROOMS);
        Vector rectangleScreenSize = size.multiply(screenTileSize);

        Vector topLeftCorner = position.subtractX(size.getX() / 2).addY(size.getY() / 2);
        int rectScreenX = (int) (minimapLeft + topLeftCorner.getX() * screenTileSize);
        int rectScreenY = (int) (minimapBottom - topLeftCorner.getY() * screenTileSize);
        int rectScreenWidth = (int) rectangleScreenSize.getX();
        int rectScreenHeight = (int) rectangleScreenSize.getY();

        g.setColor(color);
        g.fillRect(rectScreenX, rectScreenY, rectScreenWidth, rectScreenHeight);
    }
}
