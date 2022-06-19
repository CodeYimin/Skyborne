package components;

import input.Mouse;

public class MouseFire extends Component {
    @Override
    public void update(double deltaTime) {
        Mouse mouse = getGameObject().getScene().getGame().getMouse();

        if (mouse.isPressed()) {
            Weapon weapon = getGameObject().getComponent(Weapon.class);
            if (weapon != null) {
                weapon.fire();
            }
        }
    }
}
