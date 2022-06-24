package components;

import util.Timer;

/**
 * Automatically fires the weapon this component is attached to in a set
 * interval
 * 
 * @author Yimin Sun
 */
public class AutoFire extends Component {
    private Timer fireTimer;

    public AutoFire(int fireInterval) {
        this.fireTimer = new Timer(fireInterval);
    }

    @Override
    public void update(double deltaTime) {
        if (fireTimer.isDone()) {
            fireTimer.resetAndStart();
            getGameObject().getComponent(Weapon.class).fire();
        }
    }
}
