package scriptdata

import engine.entities.Entity
import engine.events.ClickEvent
import engine.events.Event
import engine.events.ImageViewEvent
import engine.events.KeyPressEvent
import engine.events.TransparentMouseEvent
import engine.events.ViewVisEvent
import engine.fsm.Transition
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent

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

entity.on("Authoring Click", { Event event ->
    ClickEvent cEvent = (ClickEvent) event
    pointer.setOnMouseClicked( { MouseEvent e -> changeRender(cEvent, e) })
})

entity.on("Authoring Key Pressed", { Event event ->
    KeyPressEvent kEvent = (KeyPressEvent) event
    pointer.setOnKeyPressed( { KeyEvent e -> deleteEntity(kEvent, e)})
})

private void changeRender(ClickEvent cEvent, MouseEvent mouseEvent) {
    if (cEvent.getIsGaming() == false) {
        pointer.requestFocus()
        if (mouseEvent.equals(MouseButton.PRIMARY) && cEvent.getMyMode()[0] == 0) {
            //might need try catch here
            cEvent.getMyBGType().reset()
            pointer.setImage(new Image(cEvent.getMyBGType()))
        }
    }
    mouseEvent.consume()
}

private void deleteEntity(KeyPressEvent kEvent, KeyEvent keyEvent) {
    if (kEvent.getIsGaming() == false) {
        if (keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
            entity.getParent().remove(entity)
        }
        keyEvent.consume()
    }
}