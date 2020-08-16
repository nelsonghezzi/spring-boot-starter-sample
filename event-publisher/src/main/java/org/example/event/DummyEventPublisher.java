package org.example.event;

import java.time.LocalDateTime;
import java.util.List;

public class DummyEventPublisher extends EventPublisher {

    private final boolean logTime;

    public DummyEventPublisher(List<EventListener> listeners, boolean logTime) {
        super(listeners);
        this.logTime = logTime;
    }

    public void publish(String eventName) {
        if (this.logTime) {
            String prefix = LocalDateTime.now().toString();
            System.out.println("[" + prefix + "] " + "Published event '" + eventName + "'");
        }

        listeners.stream()
            .filter(l -> l.getEventName().equalsIgnoreCase(eventName))
            .forEach(EventListener::onEvent);
    }
}
