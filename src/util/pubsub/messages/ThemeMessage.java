package util.pubsub.messages;

public class ThemeMessage extends Message {

    private String myMessage;

    public ThemeMessage(String message) {
        myMessage = message;
    }

    public String readMessage() {
        return myMessage;
    }
}
