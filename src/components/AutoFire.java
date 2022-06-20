package components;

import util.Timer;

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
