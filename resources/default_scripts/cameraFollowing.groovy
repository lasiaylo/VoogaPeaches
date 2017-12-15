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

    def pos = new Vector(((Number)entity.getProperty("x")).doubleValue(), ((Number)entity.getProperty("y")).doubleValue())
    def size = new Vector(((Number)entity.getProperty("width")).doubleValue()/2, ((Number)entity.getProperty("height")).doubleValue()/2)
    PubSub.getInstance().publish("MOVE_CAMERA", new MoveCameraMessage(pos.add(size)))
}