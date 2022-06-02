package entities;

import graphics.Sprite;
import scenes.World;
import world.Tile;

public class Projectile extends Entity {
    public Projectile(World world) {
        super(world);

        setSprite(new Sprite("../assets/player.jpg"));
        setPhasesTiles(true);
    }

    @Override
    public void update() {
        super.update();

        if (getTimeSinceCreation() > 2000) {
            destroy();
        }
    }

    @Override
    public void onCollision(int tile) {
        if (Tile.isSolid(tile)) {
            destroy();
        }
    }
}
