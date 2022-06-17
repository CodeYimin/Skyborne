package entities;

import util.Vector;

public interface MovementListener {
    public void onMove(Vector oldPosition, Vector newPosition);
}
