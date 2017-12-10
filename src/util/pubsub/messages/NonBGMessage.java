package util.pubsub.messages;

import util.math.num.Vector;

public class NonBGMessage extends Message {
    private String UID;
    private Vector pos;
    public NonBGMessage(String UID, Vector pos) {
        this.UID = UID;
        this.pos = pos;
    }

    public String getUID() {
        return UID;
    }

    public Vector getPos() {
        return pos;
    }
}
