package entities;

import graphics.Sprite;
import scenes.World;

public class Projectile extends Entity {
    public Projectile(World world) {
        super(world);

        setSprite(new Sprite("../assets/player.jpg"));
    }
}
