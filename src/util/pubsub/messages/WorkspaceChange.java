package util.pubsub.messages;

public class WorkspaceChange extends Message {

    private String myMessage;

    public WorkspaceChange(String message) {
        myMessage = message;
    }

    public String readMessage() {
        return myMessage;
    }
}
