package util;

import util.pubsub.PubSub;
import util.pubsub.messages.Message;
import util.pubsub.messages.ThemeMessage;
import java.util.function.Consumer;

/**
 * Test for PubSub to ensure that you can successfully publish and subscribe. The callback function is the function
 * called once a message has been published on the channel that you are subscribing to. In this case, I subscribe
 * to the TEST_MESSAGE Channel which is expected to exchange Messages, specifically TestMessages in this case.
 *
 * The list of available channels is in the Channel Enum in the PubSub class. To create a new channel, simply add
 * NAME(MessageType.class) where NAME is the channel name and MessageType is the name of the message class intended
 * to be communicated through the channel.
 *
 * @author Simran
 */

public class PubSubTest {

    public static void main(String[] args) {
        PubSub pubSub = PubSub.getInstance();
        Consumer<Message> myCallback = (message) ->
        {
            ThemeMessage newMessage = (ThemeMessage) message;
            System.out.println(newMessage.readMessage());
        };
        pubSub.subscribe(PubSub.Channel.THEME_MESSAGE, myCallback);
        pubSub.publish(PubSub.Channel.THEME_MESSAGE, new ThemeMessage("this is a test!"));
    }

}
