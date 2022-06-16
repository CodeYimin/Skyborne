package core;

public abstract class TimedUpdatable {
    private long lastUpdateTime;
    private int updateInterval;

    public TimedUpdatable(int updateInterval) {
        this.updateInterval = updateInterval;
    }

    public abstract void timedUpdate();

    public final void update() {
        if (System.currentTimeMillis() - lastUpdateTime >= updateInterval) {
            lastUpdateTime = System.currentTimeMillis();
            timedUpdate();
        }
    }
}
