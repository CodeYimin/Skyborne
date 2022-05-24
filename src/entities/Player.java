package entities;

import java.awt.Color;
import java.awt.Graphics;

import graphics.Drawable;
import input.VectorCompositeBinding;
import util.Vector;

public class Player extends GameObject implements Drawable {
    private double speed = 5;
    private Vector velocity = new Vector(0, 0);
    private VectorCompositeBinding movementControls;

    public Player(VectorCompositeBinding movementControls) {
        this.movementControls = movementControls;
    }

    @Override
    public void update() {
        Vector movementInput = movementControls.getValue();
        Vector direction = movementInput.normalize();
        velocity = direction.multiply(speed);

        move();
    }

    private void move() {
        setPosition(getPosition().add(velocity));
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect((int) getPosition().getX(),
                (int) getPosition().getY(),
                100,
                100);
    }
}
