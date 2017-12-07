package scriptdata

import database.filehelpers.FileDataFolders
import database.filehelpers.FileDataManager
import engine.entities.Entity
import engine.events.ClickEvent
import engine.events.Event
import engine.events.ImageViewEvent
import engine.events.InitialImageEvent
import engine.events.KeyPressEvent
import engine.events.MouseDragEvent
import engine.events.MousePressEvent
import engine.events.TransparentMouseEvent
import engine.events.ViewVisEvent
import engine.fsm.Transition
import engine.util.FXProcessing
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import util.math.num.Vector

import java.util.function.Consumer

entity = (Entity) entity

datamanager = new FileDataManager(FileDataFolders.IMAGES)
pointer = new ImageView(new Image(datamanager.readFileData(entity.getProperty("image path"))))
entity.add(pointer)


entity.on("Image View Event", { Event event ->
    ImageViewEvent imgEvent = (ImageViewEvent) event
    pointer.setImage(imgEvent.getImage())
})

entity.on("setup initial imageview", { Event event ->
    InitialImageEvent iEvent = (InitialImageEvent) event
    pointer.setFitWidth(iEvent.getMyGridSize())
    pointer.setFitHeight(iEvent.getMyGridSize())
    pointer.setX(FXProcessing.getXImageCoord(iEvent.getMyPos().at(0), pointer))
    pointer.setY(FXProcessing.getYImageCoord(iEvent.getMyPos().at(1), pointer))
})

entity.on("Transparent Mouse Event", { Event event ->
    TransparentMouseEvent tEvent = (TransparentMouseEvent) event
    pointer.setMouseTransparent(tEvent.getBool())
})

entity.on("View Visibility Event", { Event event ->
    ViewVisEvent visEvent = (ViewVisEvent) event
    pointer.setVisible(visEvent.getBool())
})

entity.on("click", { Event event ->
    ClickEvent cEvent = (ClickEvent) event
    pointer.setOnMouseClicked( { MouseEvent e ->
        if (cEvent.getIsGaming() == false) {
            pointer.requestFocus()
            if (e.getButton() == MouseButton.PRIMARY && cEvent.getMyMode()[0] == 0) {
                //might need try catch here
                cEvent.getMyBGType().reset()
                pointer.setImage(new Image(cEvent.getMyBGType()))
            }
        }
        e.consume()
    })
})

entity.on("key press", { Event event ->
    KeyPressEvent kEvent = (KeyPressEvent) event
    pointer.setOnKeyPressed( { KeyEvent e ->
        if (kEvent.getIsGaming() == false && e.getCode().equals(kEvent.getKeyCode())) {
            entity.getParent().remove(entity)
        }
        e.consume()
    })
})

entity.on("mouse press", { Event event ->
    MousePressEvent pEvent = (MousePressEvent) event
    pointer.setOnMousePressed( { MouseEvent e ->
        if (pEvent.getIsGaming() == false && pEvent.getMyMode()[0] > 0) {
            pEvent.setMyStartPos(e.getX(), e.getY())
            pEvent.setMyStartSize(pointer.getFitWidth(), pointer.getFitHeight())
        }
        e.consume()
    })
})

entity.on("mouse drag", { Event event ->
    MouseDragEvent dEvent = (MouseDragEvent) event
    pointer.setOnMouseDragged({ MouseEvent e ->
        if (dEvent.getIsGaming() == false && dEvent.getMyMode()[0] > 0) {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                move(e)
            } else if (e.getButton().equals(MouseButton.SECONDARY)) {
                zoom(dEvent, e)
            }
        }
        e.consume()
    })
})


void zoom(MouseDragEvent dEvent, MouseEvent mouseEvent) {
    def change = new Vector(mouseEvent.getX(), mouseEvent.getY()).subtract(dEvent.getMyStartPos())
    if (mouseEvent.getX() < dEvent.getMyStartPos().at(0)) {
        change.at(0, 0.0)
    }
    if (mouseEvent.getY() < dEvent.getMyStartPos().at(1)) {
        change.at(1, 0.0)
    }
    def fsize = change.add(dEvent.getMyStartSize())
    pointer.setFitWidth(fsize.at(0))
    pointer.setFitHeight(fsize.at(1))
}

void move(MouseEvent mouseEvent) {
    def xPos = mouseEvent.getX()
    def yPos = mouseEvent.getY()
    //LOL there is actually a bug here, if you try to drag over the right bound and lower bound
    if (mouseEvent.getX() < pointer.getFitWidth() / 2) {
        xPos = pointer.getFitWidth() / 2
    }
    if (mouseEvent.getY() < pointer.getFitHeight() / 2) {
        yPos = pointer.getFitHeight() / 2

        pointer.setX(FXProcessing.getXImageCoord(xPos, pointer))
        pointer.setY(FXProcessing.getYImageCoord(yPos, pointer))
        //need to change the location properties, too lazy to do it
    }
}