package org.example.configuration;

import org.example.event.DummyEventPublisher;
import org.example.event.EventListener;
import org.example.event.EventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConditionalOnProperty(value = "eventstarter.enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnWebApplication
@EnableConfigurationProperties(EventPublisherProperties.class)
public class EventPublisherAutoConfiguration {

    @Autowired
    EventPublisherProperties properties;

    @Bean
    EventPublisher eventPublisher(List<EventListener> listeners) {
        return new DummyEventPublisher(listeners, properties.isLogTime());
    }
}
