package util.pubsub.messages;

public class TextMessage extends Message {

    private String myMessage;

    public TextMessage(String message) {
        myMessage = message;
    }

    public String readMessage() {
        return myMessage;
    }
}
