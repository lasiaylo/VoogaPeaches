package filedata.scripts

import engine.entities.Entity
import engine.events.Event
import javafx.scene.text.Text
import util.pubsub.PubSub
import util.pubsub.messages.FSMSaveMessage
import util.pubsub.messages.TextMessage


{ Entity entity, Map<String, Object> bindings, Event event = null ->
    Text newText = new Text("Current Score: " + entity.parent.getProperties().getOrDefault("score",0))
    PubSub.getInstance().subscribe("SET_TEXT", { message ->
        println("here: " + ((TextMessage) message).readMessage())
        newText.setText(((TextMessage) message).readMessage())})
    newText.setX(500)
    newText.setY(150)
    entity.getParent().add(newText)
}