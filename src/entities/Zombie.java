package entities;

import graphics.Sprite;
import scenes.World;
import util.Size;
import util.Vector;

public class Zombie extends Entity {
    public Zombie(World world) {
        super(world);

        setSprite(new Sprite("../assets/entities/zombie.jpg"));
        setAcceleration(Vector.DOWN.multiply(15));
        setSize(new Size(2, 2));
        setVelocity(Vector.RIGHT);
    }
}
