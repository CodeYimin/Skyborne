package entities;

import scenes.World;
import util.Vector;

public class Character extends Entity {
    private Weapon weapon;

    public Character(World world) {
        super(world);
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void equipWeapon(Weapon weapon) {
        weapon.setOwner(this);
        weapon.setRelativePosition(new Vector(0, 0));
        this.weapon = weapon;
    }

    public void dequipWeapon() {
        weapon.setOwner(null);
        weapon = null;
    }
}
