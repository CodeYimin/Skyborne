package util;

public class Timer {
    private int duration;
    private boolean started = true;
    private long lastStartTime;

    public Timer(int duration) {
        this.duration = duration;
    }

    public boolean isDone() {
        return getCurrentTime() > duration;
    }

    public int getCurrentTime() {
        if (!started) {
            return 0;
        }
        return (int) (System.currentTimeMillis() - lastStartTime);
    }

    public double getPercentage() {
        return (double) getCurrentTime() / duration;
    }

    public int getMaxTime() {
        return duration;
    }

    public void resetAndStart() {
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
