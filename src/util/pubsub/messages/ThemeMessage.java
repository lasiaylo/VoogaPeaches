package util.pubsub.messages;

public class ThemeMessage extends Message {

    private String message;

    public ThemeMessage(String test) {
        message = test;
    }

    public String readMessage() {
        return message;
    }
}
