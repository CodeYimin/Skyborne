package components;

import events.EventListener;
import events.StatChangeEvent;

public class DestroyOnDeath extends Component {
    @Override
    public void start() {
        Health health = getGameObject().getComponent(Health.class);
        if (health != null) {
            health.addChangeListener(new EventListener<StatChangeEvent>(-100) {
                @Override
                public void onEvent(StatChangeEvent event) {
                    if (event.getNewValue() <= 0) {
                        getGameObject().destroy();
                    }
                }
            });
        }
    }
}
