package util;

import util.pubsub.PubSub;
import util.pubsub.messages.Message;
import util.pubsub.messages.TestMessage;
import java.util.function.Consumer;

/**
 * Test for PubSub to ensure that you can successfully publish and subscribe. The callback function is the function
 * called once a message has been published on the channel that you are subscribing to. In this case, I subscribe
 * to the TEST_MESSAGE Channel which is expected to exchange Messages, specifically TestMessages in this case.
 *
 * @author Simran
 */

public class PubSubTest {

    public static void main(String[] args) {
        PubSub pubSub = PubSub.getInstance();
        Consumer<Message> myCallback = (message) ->
        {
            TestMessage newMessage = (TestMessage) message;
            System.out.println(newMessage.readMessage());
        };
        pubSub.subscribe(PubSub.Channel.TEST_MESSAGE, myCallback);
        pubSub.publish(PubSub.Channel.TEST_MESSAGE, new TestMessage("this is a test!"));
    }

}
