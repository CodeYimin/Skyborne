package entities;

import scenes.World;
import util.Vector;

public class Weapon {
    private World world;

    public Weapon(World world) {
        this.world = world;
    }

    public Projectile shoot(Vector position, Vector velocity) {
        Projectile projectile = new Projectile(world);
        projectile.setPosition(position);
        projectile.setVelocity(velocity);

        world.instantiateEntity(projectile);
        return projectile;
    }
}
