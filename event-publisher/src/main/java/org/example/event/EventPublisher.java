package org.example.event;

import java.util.List;

public abstract class EventPublisher {

    protected final List<EventListener> listeners;

    protected EventPublisher(List<EventListener> listeners) {
        this.listeners = listeners;
    }

    public abstract void publish(String eventName);
}
