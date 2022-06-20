package components;

import core.GameObject;
import structures.Vector;
import util.Timer;

public class Weapon extends Component {
    private Class<? extends Component> targetClass;
    private double bulletSpeed;
    private Timer cooldownTimer;
    private int manaCost;

    public Weapon(Class<? extends Component> targetClass, double bulletSpeed, int manaCost, int cooldown) {
        this.targetClass = targetClass;
        this.bulletSpeed = bulletSpeed;
        this.manaCost = manaCost;
        this.cooldownTimer = new Timer(cooldown);
    }

    @Override
    public void update(double deltaTime) {
        // Allow player to pickup this weapon if they are interacting within range
        if (getGameObject().getParent() == null) {
            Player player = getGameObject().getScene().getComponent(Player.class);
            if (player != null && player.isInteracting()) {
                Transform transform = getGameObject().getTransform();
                Transform playerTransform = player.getGameObject().getTransform();

                if (transform.getPosition().distance(playerTransform.getPosition()) < 0.5) {
                    player.equipWeapon(this);
                }
            }
        }
    }

    public void fire() {
        if (!cooldownTimer.isDone()) {
            return;
        }

        Mana parentMana = getGameObject().getParent().getComponent(Mana.class);
        if (parentMana != null && parentMana.getCurrent() < manaCost) {
            return;
        }

        Transform transform = getGameObject().getTransform();

        GameObject bullet = new GameObject();
        bullet.addComponent(new BoxCollider());
        bullet.addComponent(new Bullet(targetClass));
        bullet.addComponent(new BasicMotion(new Vector(transform.getRotation()).multiply(bulletSpeed)));
        bullet.addComponent(new SpriteRenderer("../assets/coin_anim_f0.png"));
        bullet.addComponent(new AutoDestroy(3));
        bullet.getTransform().setPosition(getGameObject().getTransform().getPosition());
        bullet.getTransform().setScale(Vector.ONE.multiply(0.3));
        getGameObject().getScene().addGameObject(bullet);

        if (parentMana != null) {
            parentMana.use(manaCost);
        }

        cooldownTimer.resetAndStart();
    }
}
