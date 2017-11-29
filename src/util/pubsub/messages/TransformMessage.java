package util.pubsub.messages;


import engine.entities.Transform;

public class TransformMessage extends Message {
    private Transform myTransform;
    public TransformMessage(Transform transform) {
        myTransform = transform;
    }

    public Transform readMessage() {
        return myTransform;
    }
}
