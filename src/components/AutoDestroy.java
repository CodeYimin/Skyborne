package components;

/**
 * Destroys the game object this component is attached to after a set amount of
 * time
 * 
 * @author Yimin Sun
 */
public class AutoDestroy extends Component {
    private double timeToLive;

    public AutoDestroy(double timeToLive) {
        this.timeToLive = timeToLive;
    }

    public double getTimeToLive() {
        return timeToLive;
    }

    @Override
    public void update(double deltaTime) {
        timeToLive -= deltaTime;
        if (timeToLive <= 0) {
            getGameObject().destroy();
        }
    }
}
