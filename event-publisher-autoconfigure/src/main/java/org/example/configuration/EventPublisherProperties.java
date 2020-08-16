package org.example.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties("eventstarter.publisher")
@ConstructorBinding
public class EventPublisherProperties {

    /**
     * Makes the {@link org.example.event.EventPublisher} to log the time at which it receives
     * events
     */
    private final boolean logTime;

    private final String propA;

    private final int propB;

    public EventPublisherProperties(boolean logTime, String propA, int propB) {
        this.logTime = logTime;
        this.propA = propA;
        this.propB = propB;
    }

    public boolean isLogTime() {
        return logTime;
    }

    public String getPropA() {
        return propA;
    }

    public int getPropB() {
        return propB;
    }
}
