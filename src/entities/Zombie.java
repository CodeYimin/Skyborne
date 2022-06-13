package entities;

import graphics.Sprite;
import scenes.World;
import util.Side;
import util.Size;
import util.Vector;

public class Zombie extends Entity {
    public Zombie(World world) {
        super(world);

        setSprite(new Sprite("../assets/entities/zombie.jpg"));
        setAcceleration(Vector.DOWN.multiply(15));
        setSize(new Size(1, 1));
        setVelocity(Vector.RIGHT.multiply(1.5));
    }

    @Override
    public void update() {
        super.update();

        System.out.println(getTileCollidingSides());
        if (getTileCollidingSides().contains(Side.RIGHT) || getTileCollidingSides().contains(Side.LEFT)) {
            setDirection(getDirection().multiplyX(-1));
            setVelocity(getVelocity().withX(getDirection().x()));
        }
    }
}
