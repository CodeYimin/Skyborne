package components;

public class AutoDestroy extends Component {
    private double timeToLive;

    public AutoDestroy(double timeToLive) {
        this.timeToLive = timeToLive;
    }

    public double getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(double timeToLive) {
        this.timeToLive = timeToLive;
    }

    @Override
    public void update(double deltaTime) {
        timeToLive -= deltaTime;
        if (timeToLive <= 0) {
            getGameObject().destroy();
        }
    }
}
