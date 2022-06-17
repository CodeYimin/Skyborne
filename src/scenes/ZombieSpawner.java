package scenes;

import core.TimedUpdatable;
import entities.Zombie;
import util.Vector;

public class ZombieSpawner extends TimedUpdatable {
    private Level world;

    public ZombieSpawner(Level world, int spawnInterval) {
        super(spawnInterval);

        this.world = world;
    }

    @Override
    public void timedUpdate() {
        Zombie zombie = new Zombie(world);
        zombie.setPosition(new Vector(5, 5));
        world.instantiateEntity(zombie);
    }
}
