package event;

import java.util.List;

public abstract class EventPublisher {

    public List<EventListener> listeners;

    public abstract void publish(String eventName);
}
