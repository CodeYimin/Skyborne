package components;

import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;

import core.GameObject;
import structures.Bounds;
import structures.IntVector;
import structures.Tile;
import structures.Vector;
import util.FileUtils;

public class Tilemap extends Renderer {
    private ArrayList<Tile[][]> layers = new ArrayList<>();

    public Tilemap() {
        // Do nothing
    }

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
    public Bounds getRenderBounds() {
        if (layers.size() < 0) {
            return null;
        } else {
            return new Bounds(getGameObject().getTransform().getPosition(), new Vector(getWidth(), getHeight()));
        }
    }

    @Override
    public void render(Graphics g, Camera camera) {
        Vector tileScreenSize = Vector.ONE.multiply(camera.getZoom());
        for (int layer = 0; layer < layers.size(); layer++) {
            Tile[][] tiles = layers.get(layer);
            for (int x = 0; x < tiles.length; x++) {
                for (int y = 0; y < tiles[0].length; y++) {
                    // Bottom left -> Center
                    Vector tilePosition = getWorldPosition(new Vector(x, y), layer).add(0.5);
                    Vector tileScreenPosition = camera.worldToScreenPosition(tilePosition);

                    Tile tile = tiles[x][y];
                    if (tile != null && tile.getSprite() != null) {
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
                    Tile tile = getTileAtWorld(x, y, layer);
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
                    Tile tile = getTileAtWorld(x, y, layer);
                    if (tile != null && tile.isSolid()) {
                        intersectingTiles.add(tile);
                    }
                }
            }
        }

        return intersectingTiles;
    }

    public ArrayList<GameObject> getGameObjectsInside(Class<? extends Component> componentClass) {
        ArrayList<GameObject> objects = new ArrayList<>();
        for (GameObject object : getGameObject().getScene().getGameObjects(componentClass)) {
            if (isInside(object)) {
                objects.add(object);
            }
        }
        return objects;
    }

    public ArrayList<GameObject> getGameObjectsInside(Class<? extends Component> componentClass, int layer) {
        ArrayList<GameObject> objects = new ArrayList<>();
        for (GameObject object : getGameObject().getScene().getGameObjects(componentClass)) {
            if (isInside(object, layer)) {
                objects.add(object);
            }
        }
        return objects;
    }

    public ArrayList<GameObject> getGameObjectsInside(Class<? extends Component> componentClass, Tile[][] layer) {
        return getGameObjectsInside(componentClass, layers.indexOf(layer));
    }

    public boolean isInside(GameObject gameObject) {
        Transform transform = gameObject.getTransform();
        Vector position = transform.getPosition();
        Vector scale = transform.getScale();
        Vector localPosition = getLocalPosition(position);
        return localPosition.getX() - scale.getX() / 2 >= 0
                && localPosition.getX() + scale.getX() / 2 < getWidth()
                && localPosition.getY() - scale.getY() / 2 >= 0
                && localPosition.getY() + scale.getY() / 2 < getHeight();
    }

    public boolean isInside(GameObject gameObject, int layer) {
        Transform transform = gameObject.getTransform();
        Vector position = transform.getPosition();
        Vector scale = transform.getScale();
        Vector localPosition = getLocalPosition(position, layer);
        return localPosition.getX() - scale.getX() / 2 >= 0
                && localPosition.getX() + scale.getX() / 2 < getWidth(layer)
                && localPosition.getY() - scale.getY() / 2 >= 0
                && localPosition.getY() + scale.getY() / 2 < getHeight(layer);
    }

    public boolean isInside(GameObject gameObject, Tile[][] layer) {
        return isInside(gameObject, layers.indexOf(layer));
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

    public int getWidth(int layer) {
        return layers.get(layer).length;
    }

    public int getHeight(int layer) {
        return layers.get(layer)[0].length;
    }

    public int getWidth(Tile[][] layer) {
        return getWidth(layers.indexOf(layer));
    }

    public int getHeight(Tile[][] layer) {
        return getHeight(layers.indexOf(layer));
    }

    public IntVector getSize() {
        return new IntVector(getWidth(), getHeight());
    }

    public ArrayList<Tile[][]> getLayers() {
        return layers;
    }

    public Tile[][] getLayer(int layer) {
        return layers.get(layer);
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

    public Tile getTileAtWorld(Vector worldPosition, int layer) {
        Vector localPosition = getLocalPosition(worldPosition, layer);
        return getTileAtLocal(localPosition, layer);
    }

    public Tile getTileAtWorld(double x, double y, int layer) {
        return getTileAtWorld(new Vector(x, y), layer);
    }

    public Tile getTileAtLocal(int layer, int x, int y) {
        Tile[][] tiles = layers.get(layer);
        if (x < 0 || x >= tiles.length || y < 0 || y >= tiles[0].length) {
            return null;
        }
        return tiles[x][y];
    }

    public Tile getTileAtLocal(Vector position, int layer) {
        return getTileAtLocal(layer, (int) Math.floor(position.getX()), (int) Math.floor(position.getY()));
    }

    public Vector getWorldPosition(Vector localPosition, int layer) {
        Transform transform = getGameObject().getTransform();
        return transform.getPosition().add(localPosition).subtract(getWidth(layer) / 2.0, getHeight(layer) / 2.0);
    }

    public Vector getLocalPosition(Vector worldPosition, int layer) {
        Transform transform = getGameObject().getTransform();
        return worldPosition.subtract(transform.getPosition()).add(getWidth(layer) / 2.0, getHeight(layer) / 2.0);
    }

    public Vector getLocalPosition(Vector worldPosition) {
        Transform transform = getGameObject().getTransform();
        return worldPosition.subtract(transform.getPosition()).add(getWidth() / 2.0, getHeight() / 2.0);
    }

    public boolean isSolidAt(Vector worldPosition) {
        for (int layer = 0; layer < layers.size(); layer++) {
            Tile tile = getTileAtWorld(worldPosition, layer);
            if (tile != null && tile.isSolid()) {
                return true;
            }
        }
        return false;
    }
}
