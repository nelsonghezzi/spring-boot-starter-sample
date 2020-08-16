package org.example.configuration;

import org.example.event.DummyEventPublisher;
import org.example.event.EventListener;
import org.example.event.EventPublisher;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

public class EventPublisherAutoConfigurationTests {

    private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
        .withConfiguration(AutoConfigurations.of(EventPublisherAutoConfiguration.class));

    @Configuration
    static class UserProvidedConfiguration {

        @Bean
        EventPublisher customEventPublisher() {
            return new DummyEventPublisher(null, false);
        }
    }

    static class UserProvidedEventListener implements EventListener {

        @Override
        public String getEventName() {
            return null;
        }

        @Override
        public void onEvent() {
        }
    }

    @Test
    void autoConfigurationProvidesEventPublisherBean() {
        this.contextRunner
            .run(context -> {
                assertThat(context).hasSingleBean(EventPublisher.class);
            });
    }

    @Test
    void whenAutoConfigurationIsDisabledThenContextDoesNotHaveEventPublisherBean() {
        this.contextRunner
            .withPropertyValues("eventstarter.enabled=false")
            .run(context -> {
                assertThat(context).doesNotHaveBean(EventPublisher.class);
            });
    }

    @Test
    void whenUserDefinesEventPublisherBeanThenAutoConfigurationBacksOff() {
        this.contextRunner
            .withUserConfiguration(UserProvidedConfiguration.class)
            .run(context -> {
                assertThat(context)
                    .getBean("customEventPublisher")
                    .isSameAs(context.getBean(EventPublisher.class));
            });
    }

    @Test
    void whenUserDefinesEventListenerBeanThenAutoConfigurationRegistersItWithEventPublisherBean() {
        this.contextRunner
            .withBean(UserProvidedEventListener.class)
            .run(context -> {
                assertThat(context).getBean(EventPublisher.class)
                    .extracting("listeners").asList()
                    .size().isEqualTo(1);
            });
    }

    @Test
    void whenUserSetsLogTimePropertyThenAutoConfigurationConfiguresItInEventPublisherBean() {
        this.contextRunner
            .withPropertyValues("eventstarter.publisher.log-time=true")
            .run(context -> {
                assertThat(context).getBean(EventPublisher.class)
                    .extracting("logTime").isEqualTo(true);
            });
    }
}
