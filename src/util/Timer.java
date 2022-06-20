package util;

public class Timer {
    private int duration;
    private boolean started = true;
    private long lastStartTime;

    public Timer(int duration) {
        this.duration = duration;
    }

    public boolean isDone() {
        if (!started) {
            return false;
        }
        return lastStartTime + duration < System.currentTimeMillis();
    }

    public void start() {
        lastStartTime = System.currentTimeMillis();
        started = true;
    }

    public void resetAndStop() {
        started = false;
    }

    public boolean isStarted() {
        return started;
    }
}
