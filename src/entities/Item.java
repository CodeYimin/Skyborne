package entities;

import graphics.Sprite;
import scenes.World;
import util.Size;
import util.Vector;

public class Item extends Entity {
    private Entity owner;
    private Vector ownerPositionOffset = Vector.ZERO;

    public Item(World world) {
        super(world);

        setSprite(new Sprite("../assets/player.jpg"));
        setSize(new Size(2, 2));
    }

    @Override
    public void update() {
        super.update();

        if (owner != null) {
            setPosition(owner.getPosition().add(ownerPositionOffset));
        }
    }

    public Entity getOwner() {
        return owner;
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    public Vector getOwnerPositionOffset() {
        return ownerPositionOffset;
    }

    public void setOwnerPositionOffset(Vector ownerPositionOffset) {
        this.ownerPositionOffset = ownerPositionOffset;
    }
}
