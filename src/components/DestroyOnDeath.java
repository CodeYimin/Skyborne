package components;

import events.EventListener;
import events.HealthEvent;

public class DestroyOnDeath extends Component {
    @Override
    public void start() {
        Health health = getGameObject().getComponent(Health.class);
        if (health != null) {
            health.getEventManager().addListener(new EventListener<HealthEvent>(-100) {
                @Override
                public void onEvent(HealthEvent event) {
                    if (event.getNewHealth() <= 0) {
                        getGameObject().destroy();
                    }
                }
            });
        }
    }
}
