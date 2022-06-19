package util;

public class Timer {
    private int duration;
    private long lastResetTime;

    public Timer(int duration) {
        this.duration = duration;
    }

    public boolean isDone() {
        return lastResetTime + duration < System.currentTimeMillis();
    }

    public void reset() {
        lastResetTime = System.currentTimeMillis();
    }
}
