package entities;

import java.util.ArrayList;

import core.Updatable;
import core.UpdateTimeTracker;
import util.Vector;

public class MovementManager implements Updatable {
    private Entity entity;
    private UpdateTimeTracker updateTimeTracker;
    private double speed;
    private Vector velocity;
    private MovementController movementController;
    private ArrayList<MovementListener> listeners;

    public MovementManager(Entity entity, MovementController movementInput) {
        this.entity = entity;
        this.updateTimeTracker = new UpdateTimeTracker();
        this.movementController = movementInput;
        this.velocity = Vector.ZERO;
        this.listeners = new ArrayList<>();
        this.speed = 5;
    }

    @Override
    public void update() {
        updateTimeTracker.update();

        velocity = movementController.getMovementInput().normalized().multiply(speed);
        Vector moveAmount = velocity.multiply(updateTimeTracker.getDeltaTimeSecs());

        Vector oldPosition = entity.getPosition();
        Vector newPosition = oldPosition.add(moveAmount);
        entity.setPosition(newPosition);

        for (MovementListener listener : listeners) {
            listener.onMove(oldPosition, newPosition);
        }
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public MovementController getMovementController() {
        return movementController;
    }

    public void setMovementController(MovementController movementController) {
        this.movementController = movementController;
    }

    public void addListener(MovementListener listener) {
        listeners.add(listener);
    }

    public void removeListener(MovementListener listener) {
        listeners.remove(listener);
    }
}
