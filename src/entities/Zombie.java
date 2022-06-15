package entities;

import graphics.Sprite;
import scenes.World;
import util.Side;
import util.Size;
import util.Vector;
import world.TileCollision;

public class Zombie extends Entity {
    public Zombie(World world) {
        super(world);

        setSprite(new Sprite("../assets/entities/zombie.jpg"));
        setAcceleration(Vector.DOWN.multiply(15));
        setSize(new Size(1.5, 1.5));
        setVelocity(Vector.RIGHT.multiply(1.5));
    }

    public void kill() {
        destroy();

        Gun gun = new Gun(getWorld());
        gun.setPosition(getPosition());
        getWorld().instantiateEntity(gun);
    }

    @Override
    public void update() {
        super.update();

        // setVelocity(getVelocity().withX(getDirection().x() * 1.5));

        boolean sideColliding = getSidesAlignedWithSolid().contains(Side.LEFT)
                || getSidesAlignedWithSolid().contains(Side.RIGHT);
        boolean bottomColliding = getSidesAlignedWithSolid().contains(Side.BOTTOM);

        if (sideColliding && bottomColliding) {
            setVelocity(getVelocity().withY(10));
            if (Math.random() > 0.5) {
                setDirection(getDirection().multiplyX(-1));
                setVelocity(getVelocity().withX(getDirection().x()));
            }
        }

        if (bottomColliding) {
            if (Math.random() > 0.5) {
                setVelocity(getVelocity().withY(5));
            }

            // Tile tileBelow = getWorld().getTilemap().getTileAt(getPosition().floorX());

            // if (tileBelow == null || !tileBelow.isSolid()) {
            // setVelocity(getVelocity().multiplyX(-1).withY(10));
            // }
        }
    }

    @Override
    public void onCollision(TileCollision collision) {
        // if (collision.getPerfectlyCollidingSide() == Side.BOTTOM &&
        // !collision.getTile().isSolid()) {
        // setVelocity(getVelocity().multiplyX(-1));
        // }
    }
}
