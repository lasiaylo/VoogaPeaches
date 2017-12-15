package util.pubsub.messages;

import util.math.num.Vector;

public class MoveCameraMessage extends Message{
    private Vector pos;
    public MoveCameraMessage(Vector pos) {
        this.pos = pos;
    }

    public Vector getPos() {
        return pos;
    }
}
