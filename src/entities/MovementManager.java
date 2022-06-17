package entities;

import java.util.ArrayList;

import core.Updatable;
import core.UpdateTimeTracker;
import util.Vector;

public class MovementManager implements Updatable {
    private UpdateTimeTracker updateTimeTracker;
    private Vector position;
    private double speed;
    private Vector velocity;
    private MovementController movementController;
    private ArrayList<MovementListener> listeners;

    public MovementManager(MovementController movementInput) {
        this.updateTimeTracker = new UpdateTimeTracker();
        this.movementController = movementInput;
        this.position = Vector.ZERO;
        this.velocity = Vector.ZERO;
        this.listeners = new ArrayList<>();
        this.speed = 5;
    }

    @Override
    public void update() {
        updateTimeTracker.update();

        velocity = movementController.getMovementInput().normalized().multiply(speed);
        Vector moveAmount = velocity.multiply(updateTimeTracker.getDeltaTimeSecs());

        Vector oldPosition = position;
        position = position.add(moveAmount);

        for (MovementListener listener : listeners) {
            listener.onMove(oldPosition, position);
        }
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
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
