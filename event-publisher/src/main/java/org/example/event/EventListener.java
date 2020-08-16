package org.example.event;

public interface EventListener {
    String getEventName();

    void onEvent();
}
