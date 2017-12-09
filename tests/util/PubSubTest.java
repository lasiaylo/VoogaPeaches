package util;

import util.pubsub.PubSub;
import util.pubsub.messages.StringMessage;

public class PubSubTest {

    public static void main(String[] args) {
        PubSub pubSub = PubSub.getInstance();
        pubSub.subscribe(
                "THEME_MESSAGE",
                (message) -> System.out.println(((StringMessage) message).readMessage()));
        pubSub.getInstance().publish("THEME_MESSAGE", new StringMessage("test"));
    }
}
