package util.pubsub.messages;

public class TestMessage extends Message {

    private String message;

    public TestMessage(String test) {
        message = test;
    }

    public String readMessage() {
        return message;
    }
}
