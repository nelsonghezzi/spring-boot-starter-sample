package event;

public class DummyEventPublisher extends EventPublisher {
    public void publish(String eventName) {
        System.out.println("Published event " + eventName);
    }
}
