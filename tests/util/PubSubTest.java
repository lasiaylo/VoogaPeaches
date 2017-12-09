package util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.pubsub.PubSub;
import util.pubsub.messages.StringMessage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PubSubTest {

    private PubSub pubSub;

    @BeforeAll
    public void setUp () {
        pubSub = PubSub.getInstance();
    }

    @Test
    public void testThemeChannel () {
        PubSub pubSub = PubSub.getInstance();
        String theme = "Test";
        final String[] messages = new String[1];
        pubSub.subscribe(
                "THEME_MESSAGE",
                (message) -> messages[0] = ((StringMessage) message).readMessage());
        pubSub.getInstance().publish("THEME_MESSAGE", new StringMessage(theme));
        assertEquals("Test Theme Message", theme, messages[0]);
    }

}
