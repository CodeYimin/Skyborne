package entities;

import java.awt.Color;
import java.awt.Graphics;

import graphics.Camera;
import scenes.World;
import util.Vector;

public class Character extends Entity {
    private Weapon weapon;

    public Character(World world) {
        super(world);
    }

    @Override
    public void update() {
        super.update();

    }

    @Override
    public void render(Graphics g, Camera camera) {
        super.render(g, camera);

        Vector topLeft = new Vector(getLeft(), getTop());
        Vector screenPosition = camera
                .worldToScreenPosition(topLeft.addY(0.5).subtractX(getSize().width() / 2));

        g.setColor(Color.RED);
        g.fillRect((int) screenPosition.x(), (int) screenPosition.y(), 100, 10);
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
