package util.pubsub.messages;

public class PanelToggle extends Message {

    private String myMessage;

    public PanelToggle(String message) {
        myMessage = message;
    }

    public String readMessage() {
        return myMessage;
    }
}
