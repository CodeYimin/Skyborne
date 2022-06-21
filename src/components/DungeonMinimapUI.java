package components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import core.GameObject;
import core.GraphicsPanel;
import structures.Vector;
import util.Const;

public class DungeonMinimapUI extends UI {
    public static final int WIDTH = Const.DUNGEON_MINIMAP_WIDTH;
    public static final int HEIGHT = Const.DUNGEON_MINIMAP_HEIGHT;
    public static final int MARGIN = Const.DUNGEON_MINIMAP_MARGIN;

    @Override
    public void draw(Graphics g) {
        Dungeon dungeon = getGameObject().getComponent(Dungeon.class);
        if (dungeon == null) {
            return;
        }

        GraphicsPanel graphicsPanel = getGameObject().getScene().getGame().getWindow().getGraphicsPanel();
        int screenWidth = graphicsPanel.getWidth();

        int mapWidth = dungeon.getWidth();
        int mapHeight = dungeon.getHeight();
        GameObject[][] rooms = dungeon.getRooms();

        g.setColor(new Color(255, 255, 255, 100));
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
                        color = Color.CYAN;
                    } else if (room.isEntered()) {
                        color = new Color(75, 75, 75);
                    } else {
                        color = Color.GRAY;
                    }
                    drawRectangle(g, transform.getLocalPosition().add(Const.DUNGEON_DISTANCE_BETWEEN_ROOMS / 2), new Vector(room.getWidth(), room.getHeight()), color, true);

                    ((Graphics2D) g).setStroke(new BasicStroke(2));
                    drawRectangle(g, transform.getLocalPosition().add(Const.DUNGEON_DISTANCE_BETWEEN_ROOMS / 2), new Vector(room.getWidth(), room.getHeight()), Color.BLACK, false);
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
            drawRectangle(g, playerLocalPosition.add(Const.DUNGEON_DISTANCE_BETWEEN_ROOMS / 2), Vector.ONE.multiply(5), Color.BLACK, true);
        }

        g.setColor(Color.BLACK);
        ((Graphics2D) g).setStroke(new BasicStroke(5));
        g.drawRect(screenWidth - WIDTH - MARGIN, MARGIN, WIDTH, HEIGHT);
    }

    private void drawRectangle(Graphics g, Vector position, Vector size, Color color, boolean fill) {
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

        double screenTileSize = (double) WIDTH / (mapWidth * Const.DUNGEON_DISTANCE_BETWEEN_ROOMS);
        Vector rectangleScreenSize = size.multiply(screenTileSize);

        Vector topLeftCorner = position.subtractX(size.getX() / 2).addY(size.getY() / 2);
        int rectScreenX = (int) (minimapLeft + topLeftCorner.getX() * screenTileSize);
        int rectScreenY = (int) (minimapBottom - topLeftCorner.getY() * screenTileSize);
        int rectScreenWidth = (int) rectangleScreenSize.getX();
        int rectScreenHeight = (int) rectangleScreenSize.getY();

        g.setColor(color);
        if (fill) {
            g.fillRect(rectScreenX, rectScreenY, rectScreenWidth, rectScreenHeight);
        } else {
            g.drawRect(rectScreenX, rectScreenY, rectScreenWidth, rectScreenHeight);
        }
    }
}
