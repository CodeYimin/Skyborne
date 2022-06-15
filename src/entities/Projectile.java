package entities;

import graphics.Sprite;
import scenes.World;
import util.Size;
import world.TileCollision;

public class Projectile extends Entity {
    public Projectile(World world) {
        super(world);

        setSprite(new Sprite("../assets/player.jpg"));
        setPhasesTiles(true);
        setSize(new Size(0.3, 0.3));
    }

    @Override
    public void update() {
        super.update();

        if (getTimeSinceCreation() > 2000) {
            destroy();
        }
    }

    @Override
    public void onCollision(TileCollision collision) {
        if (collision.getTile().isSolid()) {
            destroy();
        }
    }

    @Override
    public void onCollision(Entity entity) {
        if (entity instanceof Zombie) {
            ((Zombie) entity).kill();
            destroy();
        }
    }
}
