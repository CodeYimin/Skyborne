package components;

import core.GameObject;
import events.EventListener;
import events.StatChangeEvent;
import structures.Vector;
import util.Const;

public class Enemy extends Component {
    @Override
    public void start() {
        getGameObject().addEventListener(new EventListener<>(StatChangeEvent.class, 0) {
            @Override
            public void onEvent(StatChangeEvent event) {
                if (event.getStat() instanceof Health && event.getNewValue() <= 0) {
                    spawnDeathLoot();
                }
            }
        });
    }

    private void spawnDeathLoot() {
        int manaPerOrb = Const.MANA_PER_ENEMY_MANA_ORB;
        int maxManaOrbs = Const.MAX_ENEMY_MANA_ORB_DROPS;

        int manaOrbs = (int) (Math.random() * (maxManaOrbs + 1));

        for (int i = 0; i < manaOrbs; i++) {
            GameObject manaOrb = new GameObject();
            manaOrb.addComponent(new StatOrb<Mana>(Mana.class, manaPerOrb));
            manaOrb.addComponent(new BoxCollider());
            manaOrb.addComponent(new SpriteRenderer(Const.MANA_ORB_SPRITE_PATH));
            manaOrb.getTransform().setPosition(getGameObject().getTransform().getPosition().moveRandom(1));
            manaOrb.getTransform().setScale(Vector.ONE.multiply(0.3));
            getGameObject().getScene().addGameObject(manaOrb);
        }

        if (Math.random() < Const.ENEMY_HEALTH_ORB_DROP_CHANCE) {
            GameObject healthOrb = new GameObject();
            healthOrb.addComponent(new StatOrb<Health>(Health.class, 1));
            healthOrb.addComponent(new BoxCollider());
            healthOrb.addComponent(new SpriteRenderer(Const.HEALTH_ORB_SPRITE_PATH));
            healthOrb.getTransform().setPosition(getGameObject().getTransform().getPosition().moveRandom(1));
            healthOrb.getTransform().setScale(Vector.ONE.multiply(0.3));
            getGameObject().getScene().addGameObject(healthOrb);
        }
    }
}
