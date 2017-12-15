package scripts

import engine.collisions.HitBox
import engine.entities.Entity
import engine.events.Event
import engine.events.TickEvent
import util.math.num.Vector
import util.pubsub.PubSub
import util.pubsub.messages.MoveCameraMessage

{ Entity entity, Map<String, Object> bindings, Event event = null ->
    event = (TickEvent) event

    def pos = new Vector((double)entity.getProperty("x"), (double)entity.getProperty("y"))
    def size = new Vector((double)entity.getProperty("width")/2 - 175, (double)entity.getProperty("height")/2 -175)
    PubSub.getInstance().publish("MOVE_CAMERA", new MoveCameraMessage(pos.add(size)))
}