package scriptdata

import engine.entities.Entity
import engine.events.Event
import engine.events.ImageViewEvent
import engine.events.TransparentMouseEvent
import engine.events.ViewVisEvent
import engine.fsm.Transition
import javafx.scene.image.Image
import javafx.scene.image.ImageView

entity = (Entity) entity;

ImageView pointer = new ImageView(new Image((String) entity.getProperty("image path")))
entity.getNodes().getChildren().add(pointer)

entity.on("Image View Event", { Event event ->
    ImageViewEvent imgEvent = (ImageViewEvent) event

    pointer.setImage(imgEvent.getImage())
})

entity.on("Transparent Mouse Event", { Event event ->
    TransparentMouseEvent tEvent = (TransparentMouseEvent) event
    pointer.setMouseTransparent(tEvent.getBool())
})

entity.on("View Visibility Event", { Event event ->
    ViewVisEvent visEvent = (ViewVisEvent) event
    pointer.setVisible(visEvent.getBool())
})

