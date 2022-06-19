package components;

import core.GameObject;
import structures.Vector;
import util.Timer;

public class Weapon extends Component {
    private double bulletSpeed;
    private Timer cooldownTimer;

    public Weapon(double bulletSpeed, int cooldown) {
        this.bulletSpeed = bulletSpeed;
        this.cooldownTimer = new Timer(cooldown);
    }

    public void fire() {
        if (!cooldownTimer.isDone()) {
            return;
        }

        Transform transform = getGameObject().getComponent(Transform.class);

        GameObject bullet = new GameObject();
        bullet.addComponent(new Transform(transform.getPosition(), Vector.ONE.multiply(0.3)));
        bullet.addComponent(new BasicMotion(new Vector(transform.getRotation()).multiply(bulletSpeed)));
        bullet.addComponent(new SpriteRenderer("../assets/coin_anim_f0.png"));
        bullet.addComponent(new AutoDestroy(3));
        getGameObject().getScene().addGameObject(bullet);

        cooldownTimer.reset();
    }
}
