package entities;

import util.Vector;

public class Weapon {
    private Entity owner;
    private int delay = 100;
    private long lastShootTime = 0;

    public Weapon(Entity owner) {
        this.owner = owner;
    }

    public long getTimeSinceShoot() {
        return System.currentTimeMillis() - lastShootTime;
    }

    public Projectile shoot() {
        if (getTimeSinceShoot() < delay) {
            return null;
        }

        Projectile projectile = new Projectile(owner.getWorld());
        projectile.setPosition(owner.getPosition());
        if (owner.getDirection().x() > 0) {
            projectile.setVelocity(Vector.RIGHT.multiply(10));
        } else {
            projectile.setVelocity(Vector.LEFT.multiply(10));
        }

        owner.getWorld().instantiateEntity(projectile);
        lastShootTime = System.currentTimeMillis();
        return projectile;
    }
}
