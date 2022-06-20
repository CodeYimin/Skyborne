package components;

import core.GameObject;
import structures.Vector;

public class Enemy extends Component {
    @Override
    public void stop() {
        int manaPerDrop = 3;
        int maxManaDrops = 3;

        int manaDrops = (int) (Math.random() * (maxManaDrops + 1));

        for (int i = 0; i < manaDrops; i++) {
            GameObject manaOrb = new GameObject();
            manaOrb.addComponent(new ManaOrb(manaPerDrop));
            manaOrb.addComponent(new BoxCollider());
            manaOrb.addComponent(new SpriteRenderer("../assets/wall_banner_blue.png"));
            manaOrb.getTransform().setPosition(getGameObject().getTransform().getPosition().moveRandom(1));
            manaOrb.getTransform().setScale(Vector.ONE.multiply(0.3));
            getGameObject().getScene().addGameObject(manaOrb);
        }
    }
}
