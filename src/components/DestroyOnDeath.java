package components;

public class DestroyOnDeath extends Component {
    @Override
    public void update(double deltaTime) {
        if (getGameObject().getComponent(Health.class).getHealth() <= 0) {
            getGameObject().destroy();
        }
    }
}
