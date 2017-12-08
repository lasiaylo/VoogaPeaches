package util;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import util.pubsub.PubSub;
import util.pubsub.messages.ThemeMessage;

import static org.junit.Assert.assertEquals;

public class PubSubTest {

    private PubSub pubSub;

    @Before
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
                (message) -> messages[0] = ((ThemeMessage) message).readMessage());
        pubSub.getInstance().publish("THEME_MESSAGE", new ThemeMessage(theme));
        assertEquals("Test Theme Message", theme, messages[0]);

    }
}
