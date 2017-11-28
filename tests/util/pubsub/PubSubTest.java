package util.pubsub;

import org.junit.jupiter.api.Test;
import util.pubsub.messages.ExceptionMessage;
import util.pubsub.messages.TestMessage;

import static org.junit.jupiter.api.Assertions.*;

class PubSubTest {
    @Test
    void singleton() {
        PubSub pubsub = PubSub.getInstance();
        if (pubsub == null)
            return;
        pubsub.subscribe("TEST", (msg) -> System.out.println((TestMessage) msg));
        pubsub.publish("test", new TestMessage());
    }
}