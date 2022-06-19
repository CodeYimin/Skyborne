package components;

public class MotionSpriteFlipper extends Component {
    @Override
    public void update(double deltaTime) {
        SpriteRenderer sprite = getGameObject().getComponent(SpriteRenderer.class);
        Motion motion = getGameObject().getComponent(Motion.class);
        if (sprite == null || motion == null) {
            return;
        }

        if (motion.getVelocity().getX() > 0) {
            sprite.setFlipX(false);
        } else if (motion.getVelocity().getX() < 0) {
            sprite.setFlipX(true);
        }
    }
}
