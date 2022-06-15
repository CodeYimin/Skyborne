package entities;

import scenes.World;
import util.Vector;

public class Gun extends Item {
    private int delay = 100;
    private long lastShootTime = 0;
    private int ammo = 10;

    public Gun(World world) {
        super(world);
    }

    public long getTimeSinceShoot() {
        return System.currentTimeMillis() - lastShootTime;
    }

    @Override
    public void update() {
        super.update();

        if (ammo == 0) {
            if (getOwner() instanceof Player) {
                ((Player) getOwner()).dequipWeapon();
            }
            destroy();
        }
    }

    public Projectile shoot() {
        if (getTimeSinceShoot() < delay || ammo == 0) {
            return null;
        }

        ammo--;

        Projectile projectile = new Projectile(getOwner().getWorld());
        projectile.setPosition(getPosition().addY(getSize().height() / 2));
        if (getOwner().getDirection().x() > 0) {
            projectile.setVelocity(Vector.RIGHT.multiply(10));
        } else {
            projectile.setVelocity(Vector.LEFT.multiply(10));
        }

        getOwner().getWorld().instantiateEntity(projectile);
        lastShootTime = System.currentTimeMillis();
        return projectile;
    }

    @Override
    public void onCollision(Entity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (player.getWeapon() == null) {
                player.equipWeapon(this);
            }
        }
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }
}
