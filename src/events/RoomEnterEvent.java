package events;

public class RoomEnterEvent implements Event {
    private final boolean isNewroom;

    public RoomEnterEvent(boolean isNewRoom) {
        this.isNewroom = isNewRoom;
    }

    public boolean isNewRoom() {
        return isNewroom;
    }
}
