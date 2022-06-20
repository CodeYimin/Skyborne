package components;

import core.GameObject;
import structures.Vector;

public class Enemy extends Component {
    @Override
    public void stop() {
        int manaPerOrb = 3;
        int maxManaOrbs = 3;

        int manaOrbs = (int) (Math.random() * (maxManaOrbs + 1));

        for (int i = 0; i < manaOrbs; i++) {
            GameObject manaOrb = new GameObject();
            manaOrb.addComponent(new StatOrb<Mana>(Mana.class, manaPerOrb));
            manaOrb.addComponent(new BoxCollider());
            manaOrb.addComponent(new SpriteRenderer("../assets/wall_banner_blue.png"));
            manaOrb.getTransform().setPosition(getGameObject().getTransform().getPosition().moveRandom(1));
            manaOrb.getTransform().setScale(Vector.ONE.multiply(0.3));
            getGameObject().getScene().addGameObject(manaOrb);
        }

        if (Math.random() < 0.25) {
            GameObject healthOrb = new GameObject();
            healthOrb.addComponent(new StatOrb<Health>(Health.class, 1));
            healthOrb.addComponent(new BoxCollider());
            healthOrb.addComponent(new SpriteRenderer("../assets/wall_banner_red.png"));
            healthOrb.getTransform().setPosition(getGameObject().getTransform().getPosition().moveRandom(1));
            healthOrb.getTransform().setScale(Vector.ONE.multiply(0.3));
            getGameObject().getScene().addGameObject(healthOrb);
        }
    }
}
