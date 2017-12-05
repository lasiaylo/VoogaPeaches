package scriptdata

import engine.entities.Entity
import engine.events.Event
import engine.events.ImageViewEvent
import javafx.scene.image.Image
import javafx.scene.image.ImageView

entity = (Entity) entity;

ImageView pointer = new ImageView(new Image(entity.getProperty("image path")))
entity.getNodes().getChildren().add(pointer)

entity.on("setView", { Event event ->
    ImageViewEvent imgEvent = (ImageViewEvent) event

    pointer.setImage(imgEvent.getImage())
})


new ImageViewEvent(new Image("something")).fire(entity)