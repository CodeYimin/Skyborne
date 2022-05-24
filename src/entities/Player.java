package entities;

import java.awt.Color;
import java.awt.Graphics;

import core.Updatable;
import graphics.Drawable;
import input.VectorCompositeBinding;
import util.Vector;

public class Player extends GameObject implements Drawable, Updatable {
    private double speed = 5;
    private Vector velocity = new Vector(0, 0);
    private VectorCompositeBinding movementControls;

    public Player(VectorCompositeBinding movementControls) {
        this.movementControls = movementControls;
    }

    @Override
    public void update() {
        Vector movement = movementControls.getValue(null);
        Vector direction = movement.normalize();
        velocity = direction.multiply(speed);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 100, 100);
    }
}
