package components;

import core.GameObject;

public class ManaOrb extends Component {
    private int mana;

    public ManaOrb(int mana) {
        this.mana = mana;
    }

    @Override
    public void update(double deltaTime) {
        BoxCollider boxCollider = getGameObject().getComponent(BoxCollider.class);
        if (boxCollider == null) {
            return;
        }

        for (GameObject gameObject : boxCollider.getCollidingObjects()) {
            Mana manaComponent = gameObject.getComponent(Mana.class);
            if (!getGameObject().isDestroyed() && manaComponent != null) {
                manaComponent.add(mana);
                getGameObject().destroy();
            }
        }
    }
}
