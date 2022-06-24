package components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import core.GameObject;
import core.GraphicsPanel;
import structures.Vector;
import util.Const;
import util.GraphicsUtils;

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

        // Map Container
        g.setColor(new Color(255, 255, 255, 100));
        g.fillRect(screenWidth - WIDTH - MARGIN, MARGIN, WIDTH, HEIGHT);

        // Map Container Outline
        g.setColor(Color.BLACK);
        ((Graphics2D) g).setStroke(new BasicStroke(5));
        g.drawRect(screenWidth - WIDTH - MARGIN, MARGIN, WIDTH, HEIGHT);

        // Level Container
        g.setColor(new Color(255, 255, 255, 100));
        g.fillRect(screenWidth - WIDTH - MARGIN, MARGIN * 2 + HEIGHT, WIDTH, 24);

        // Level Container Outline
        g.setColor(Color.BLACK);
        ((Graphics2D) g).setStroke(new BasicStroke(5));
        g.drawRect(screenWidth - WIDTH - MARGIN, MARGIN * 2 + HEIGHT, WIDTH, 24);

        // Level
        GraphicsUtils.drawCenteredString(g, "Level " + dungeon.getLevel(), screenWidth - MARGIN - WIDTH / 2, MARGIN * 2 + HEIGHT + 12, Color.BLACK, 18);

        // Rooms
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                if (rooms[x][y] == null) {
                    continue;
                }

                Transform transform = rooms[x][y].getTransform();
                Room room = rooms[x][y].getComponent(Room.class);
                if (room.isDiscovered()) {
                    Color color;
                    if (room.getGameObjectsInside(Player.class).size() > 0) {
                        if (room.isCleared()) {
                            color = Color.GREEN;
                        } else {
                            color = Color.RED;
                        }
                    } else if (room.isEntered()) {
                        color = new Color(75, 75, 75);
                    } else {
                        color = Color.GRAY;
                    }
                    drawRectangle(g, transform.getLocalPosition().add(Const.DUNGEON_DISTANCE_BETWEEN_ROOMS / 2), room.getSize().toVector(), color, true);

                    ((Graphics2D) g).setStroke(new BasicStroke(1));
                    drawRectangle(g, transform.getLocalPosition().add(Const.DUNGEON_DISTANCE_BETWEEN_ROOMS / 2), room.getSize().toVector(), Color.BLACK, false);
                }
            }
        }

        // Hallways
        for (GameObject hallwayObject : dungeon.getHallways()) {
            Transform transform = hallwayObject.getTransform();
            Hallway hallway = hallwayObject.getComponent(Hallway.class);

            if (hallway.isDiscovered()) {
                Color color;
                if (hallway.isEntered()) {
                    color = new Color(75, 75, 75);
                } else {
                    color = Color.GRAY;
                }
                drawRectangle(g, transform.getLocalPosition().add(Const.DUNGEON_DISTANCE_BETWEEN_ROOMS / 2), hallway.getSize().toVector(), color, true);

                ((Graphics2D) g).setStroke(new BasicStroke(1));
                drawRectangle(g, transform.getLocalPosition().add(Const.DUNGEON_DISTANCE_BETWEEN_ROOMS / 2), hallway.getSize().toVector(), Color.BLACK, false);
            }
        }

        // Player
        Player player = getGameObject().getScene().getComponent(Player.class);
        if (player != null) {
            Transform playerTransform = player.getGameObject().getTransform();
            Vector playerPosition = playerTransform.getPosition();
            Transform dungeonTransform = dungeon.getGameObject().getTransform();
            Vector dungeonPosition = dungeonTransform.getPosition();

            Vector playerLocalPosition = playerPosition.subtract(dungeonPosition);
            drawRectangle(g, playerLocalPosition.add(Const.DUNGEON_DISTANCE_BETWEEN_ROOMS / 2), Vector.ONE.multiply(5), Color.BLACK, true);
        }
    }

    /**
     * Draws a rectangle on the map given position and size local to the dungeon
     * 
     * @param g
     * @param position
     * @param size
     * @param color
     * @param fill
     */
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
