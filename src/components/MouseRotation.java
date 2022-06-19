package components;

import structures.Vector;

public class MouseRotation extends Component {
    @Override
    public void update(double deltaTime) {
        Transform transform = getGameObject().getTransform();
        Camera camera = getGameObject().getScene().getComponent(Camera.class);
        if (transform == null || camera == null) {
            return;
        }

        Vector mouseScreenPosition = getGameObject().getScene().getGame().getMouse().getPosition();
        if (mouseScreenPosition != null) {
            Vector mousePosition = camera.screenToWorldPosition(mouseScreenPosition);
            Vector direction = mousePosition.subtract(transform.getPosition());
            transform.setRotation(direction.toAngle());
        }
    }
}
