package core;

public abstract class TimedUpdatable implements Updatable {
    private final long creationTime = System.currentTimeMillis();
    private long lastUpdateTime = 0;
    private long deltaTime = 0;

    public TimedUpdatable() {
        // Do nothing
    }

    @Override
    public void update() {
        if (lastUpdateTime == 0) {
            lastUpdateTime = System.currentTimeMillis();
        }
        deltaTime = System.currentTimeMillis() - lastUpdateTime;
        lastUpdateTime = System.currentTimeMillis();
    }

    public long getDeltaTime() {
        return deltaTime;
    }

    public double getDeltaTimeSecs() {
        return deltaTime / 1000.0;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getTimeSinceCreation() {
        return System.currentTimeMillis() - creationTime;
    }
}
