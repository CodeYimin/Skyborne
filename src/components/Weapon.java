package components;

import core.GameObject;
import structures.Vector;

public class Weapon extends Component {
    private int cooldown;
    private int cooldownTimer;

    public Weapon(int cooldown) {
        this.cooldown = cooldown;
    }

    public void update(double deltaTime) {
        cooldownTimer -= (int) (deltaTime * 1000);
        if (cooldownTimer <= 0) {
            cooldownTimer = 0;
        }
    }

    public boolean isReady() {
        return cooldownTimer <= 0;
    }

    public void fire() {
        if (!isReady()) {
            return;
        }

        Transform transform = getGameObject().getComponent(Transform.class);

        GameObject bullet = new GameObject();
        bullet.addComponent(new Transform(transform.getPosition(), Vector.ONE.multiply(0.3)));
        bullet.addComponent(new BasicMotion(new Vector(transform.getRotation()).multiply(10)));
        bullet.addComponent(new SpriteRenderer("../assets/coin_anim_f0.png"));
        bullet.addComponent(new AutoDestroy(3));
        getGameObject().getScene().addGameObject(bullet);

        cooldownTimer = cooldown;
    }
}
