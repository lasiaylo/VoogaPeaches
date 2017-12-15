package filedata.scripts

import engine.entities.Entity
import engine.events.Event
import util.pubsub.PubSub
import util.pubsub.messages.TextMessage

{ Entity entity, Map<String, Object> bindings, Event event = null ->
    if((double) entity.getProperty("x") < 0 ){
        entity.setProperty("score", entity.getProperty("score") + 1)
        PubSub.getInstance().publish("SET_TEXT", new TextMessage(""))
        entity.getParent().remove(entity)
    }
}
