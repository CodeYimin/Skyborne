package core;

public class UpdateInfo {
    public final Time time;
    public final double deltaTimeMs;
    public final double deltaTimeSeconds;

    public UpdateInfo(Time time) {
        this.time = time;
        this.deltaTimeMs = time.getDeltaTime();
        this.deltaTimeSeconds = deltaTimeMs / 1000.0;
    }
}
