package components;

import core.GameObject;
import structures.Vector;
import util.Timer;

public class Weapon extends Component {
    private Class<? extends Component> targetClass;
    private double bulletSpeed;
    private Timer cooldownTimer;

    public Weapon(Class<? extends Component> targetClass, double bulletSpeed, int cooldown) {
        this.targetClass = targetClass;
        this.bulletSpeed = bulletSpeed;
        this.cooldownTimer = new Timer(cooldown);
    }

    @Override
    public void update(double deltaTime) {
        if (getGameObject().getParent() == null) {
            Player player = getGameObject().getScene().getComponent(Player.class);
            if (player != null && player.isInteracting()) {
                Transform transform = getGameObject().getComponent(Transform.class);
                Transform playerTransform = player.getGameObject().getComponent(Transform.class);

                if (transform.getPosition().distance(playerTransform.getPosition()) < 0.5) {
                    player.equip(getGameObject());
                }
            }
        }
    }

    public void fire() {
        if (getGameObject().getParent() == null) {
            return;
        }
        Motion motion = getGameObject().getParent().getComponent(Motion.class);

        if (!cooldownTimer.isDone() || (motion != null && motion.getState() == Motion.FROZEN)) {
            return;
        }

        Transform transform = getGameObject().getComponent(Transform.class);

        GameObject bullet = new GameObject();
        bullet.addComponent(new Transform(transform.getPosition(), Vector.ONE.multiply(0.3)));
        bullet.addComponent(new BoxCollider());
        bullet.addComponent(new Bullet(targetClass));
        bullet.addComponent(new BasicMotion(new Vector(transform.getRotation()).multiply(bulletSpeed)));
        bullet.addComponent(new SpriteRenderer("../assets/coin_anim_f0.png"));
        bullet.addComponent(new AutoDestroy(3));
        getGameObject().getScene().addGameObject(bullet);

        cooldownTimer.reset();
    }
}
