package core;

public class Time {
    private final long creationTime = System.currentTimeMillis();
    private long lastUpdateTime;

    public long getCreationTime() {
        return creationTime;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void update() {
        lastUpdateTime = System.currentTimeMillis();
    }

    public long getDeltaTime() {
        if (lastUpdateTime == 0) {
            return 0;
        }
        return System.currentTimeMillis() - lastUpdateTime;
    }
}
