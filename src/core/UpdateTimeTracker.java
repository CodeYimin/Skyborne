package core;

public class UpdateTimeTracker {
    private long lastUpdateTime = 0;
    private long deltaTime = 0;

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
}
