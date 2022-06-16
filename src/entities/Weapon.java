package entities;

import graphics.Sprite;
import scenes.World;
import util.Size;
import util.Vector;

public abstract class Weapon extends Entity {
    private Entity owner;
    private Vector relativePosition = Vector.ZERO;

    public Weapon(World world) {
        super(world);

        setSprite(new Sprite("../assets/player.jpg"));
        setSize(new Size(1, 1));
    }

    @Override
    public void update() {
        super.update();

        if (owner != null) {
            setPosition(owner.getPosition().add(relativePosition));
        }
    }

    public abstract Projectile shoot();

    public Entity getOwner() {
        return owner;
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    public Vector getRelativePosition() {
        return relativePosition;
    }

    public void setRelativePosition(Vector relativePosition) {
        this.relativePosition = relativePosition;
    }
}
