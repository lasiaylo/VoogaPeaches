package util.pubsub.messages;

import util.math.num.Vector;

public class BGMessage extends Message {
    private Vector pos;
    public BGMessage(Vector pos) {
        this.pos = pos;
    }

    public Vector getPos() {
        return pos;
    }
}
