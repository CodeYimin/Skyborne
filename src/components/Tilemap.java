package components;

import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;

import structures.Tile;
import structures.Vector;
import util.FileUtils;

public class Tilemap extends Renderer {
    private ArrayList<Tile[][]> layers = new ArrayList<>();

    public Tilemap(Tile[][] tiles) {
        addLayer(0, tiles);
    }

    public Tilemap(int[][] tileIds) {
        addLayer(0, tileIds);
    }

    public Tilemap(String filePath) {
        File file = new File(filePath);
        int[][] tileIds = FileUtils.processIntGrid(file);
        if (tileIds != null) {
            addLayer(0, tileIds);
        }
    }

    @Override
    public void start() {
        Transform transform = getGameObject().getComponent(Transform.class);
        if (transform == null) {
            return;
        }

        transform.setScale(new Vector(layers.get(0).length, layers.get(0)[0].length));
    }

    @Override
    public void render(Graphics g, Camera camera) {
        Vector tileScreenSize = Vector.ONE.multiply(camera.getZoom());

        for (int layer = 0; layer < layers.size(); layer++) {
            Tile[][] tiles = layers.get(layer);
            for (int x = 0; x < tiles.length; x++) {
                for (int y = 0; y < tiles[0].length; y++) {
                    // Bottom left -> Center
                    Vector tilePosition = getWorldPosition(layer, new Vector(x, y)).add(0.5);
                    Vector tileScreenPosition = camera.worldToScreenPosition(tilePosition);

                    Tile tile = tiles[x][y];
                    if (tile.getSprite() != null) {
                        tile.getSprite().draw(g, tileScreenPosition, tileScreenSize, 0, false, false);
                    }
                }
            }
        }
    }

    public ArrayList<Tile> getCollidingTiles(BoxCollider boxCollider) {
        ArrayList<Tile> collidingTiles = new ArrayList<>();

        for (int layer = 0; layer < layers.size(); layer++) {
            for (int x = (int) Math.ceil(boxCollider.getLeft()) - 1; x <= boxCollider.getRight(); x++) {
                for (int y = (int) Math.ceil(boxCollider.getBottom()) - 1; y <= boxCollider.getTop(); y++) {
                    Tile tile = getTileAtWorld(layer, x, y);
                    if (tile != null && tile.isSolid()) {
                        collidingTiles.add(tile);
                    }
                }
            }
        }

        return collidingTiles;
    }

    public ArrayList<Tile> getIntersectingTiles(BoxCollider boxCollider) {
        ArrayList<Tile> intersectingTiles = new ArrayList<>();

        for (int layer = 0; layer < layers.size(); layer++) {
            for (int x = (int) Math.floor(boxCollider.getLeft()); x < boxCollider.getRight(); x++) {
                for (int y = (int) Math.floor(boxCollider.getBottom()); y < boxCollider.getTop(); y++) {
                    Tile tile = getTileAtWorld(layer, x, y);
                    if (tile != null && tile.isSolid()) {
                        intersectingTiles.add(tile);
                    }
                }
            }
        }

        return intersectingTiles;
    }

    public int getWidth() {
        int maxWidth = 0;
        for (Tile[][] layer : layers) {
            if (layer.length > maxWidth) {
                maxWidth = layer.length;
            }
        }
        return maxWidth;
    }

    public int getHeight() {
        int maxHeight = 0;
        for (Tile[][] layer : layers) {
            if (layer[0].length > maxHeight) {
                maxHeight = layer[0].length;
            }
        }
        return maxHeight;
    }

    public ArrayList<Tile[][]> getLayers() {
        return layers;
    }

    public void addLayer(Tile[][] tiles) {
        this.layers.add(tiles);
    }

    public void addLayer(int layerIndex, Tile[][] tiles) {
        this.layers.add(layerIndex, tiles);
    }

    public void addLayer(int layerIndex, int[][] tileIds) {
        Tile[][] tiles = new Tile[tileIds.length][tileIds[0].length];
        for (int i = 0; i < tileIds.length; i++) {
            for (int j = 0; j < tileIds[0].length; j++) {
                tiles[i][j] = new Tile(tileIds[i][j]);
            }
        }
        this.layers.add(layerIndex, tiles);
    }

    public boolean removeLayer(Tile[][] tiles) {
        return this.layers.remove(tiles);
    }

    public Tile[][] removeLayer(int layerIndex) {
        return this.layers.remove(layerIndex);
    }

    public Tile getTileAtWorld(int layer, Vector worldPosition) {
        Vector localPosition = getLocalPosition(layer, worldPosition);
        return getTileAtLocal(layer, localPosition);
    }

    public Tile getTileAtWorld(int layer, double x, double y) {
        return getTileAtWorld(layer, new Vector(x, y));
    }

    public Tile getTileAtLocal(int layer, int x, int y) {
        Tile[][] tiles = layers.get(layer);
        if (x < 0 || x >= tiles.length || y < 0 || y >= tiles[0].length) {
            return null;
        }
        return tiles[x][y];
    }

    public Tile getTileAtLocal(int layer, Vector position) {
        return getTileAtLocal(layer, (int) Math.floor(position.getX()), (int) Math.floor(position.getY()));
    }

    public Vector getWorldPosition(int layer, Vector localPosition) {
        Transform transform = getGameObject().getComponent(Transform.class);
        return transform.getPosition().add(localPosition).subtract(layers.get(layer).length / 2, layers.get(layer)[0].length / 2);
    }

    public Vector getLocalPosition(int layer, Vector worldPosition) {
        Transform transform = getGameObject().getComponent(Transform.class);
        return worldPosition.subtract(transform.getPosition()).add(layers.get(layer).length / 2, layers.get(layer)[0].length / 2);
    }

    public Vector getLocalPosition(Vector worldPosition) {
        Transform transform = getGameObject().getComponent(Transform.class);
        return worldPosition.subtract(transform.getPosition()).add(getWidth() / 2, getHeight() / 2);
    }
}
