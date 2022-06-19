package components;

import structures.Vector;

public class EnemyAim extends Component {
    @Override
    public void update(double deltaTime) {
        Transform transform = getGameObject().getComponent(Transform.class);
        Transform playerTransform = getGameObject().getScene().getGameObject(Player.class).getComponent(Transform.class);

        if (transform == null || playerTransform == null) {
            return;
        }

        Vector direction = playerTransform.getPosition().subtract(transform.getPosition());
        transform.setRotation(direction.toAngle());
    }
}
