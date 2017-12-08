package scriptdata

import database.filehelpers.FileDataFolders
import database.filehelpers.FileDataManager
import engine.entities.Entity
import engine.events.ClickEvent
import engine.events.Event
import engine.events.EventType
import engine.events.ImageViewEvent
import engine.events.InitialImageEvent
import engine.events.MouseDragEvent
import engine.events.TransparentMouseEvent
import engine.events.ViewVisEvent
import engine.util.FXProcessing
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import util.math.num.Vector
import util.pubsub.PubSub
import util.pubsub.messages.EntityPass


entity = (Entity) entity

datamanager = new FileDataManager(FileDataFolders.IMAGES)
pointer = new ImageView(new Image(datamanager.readFileData((String) entity.getProperty("image path"))))
entity.add(pointer)

entity.on(EventType.IMAGE_VIEW.getType(), { Event event ->
    ImageViewEvent imgEvent = (ImageViewEvent) event
    pointer.setImage(imgEvent.getImage())
})

entity.on(EventType.INITIAL_IMAGE.getType(), { Event event ->
    InitialImageEvent iEvent = (InitialImageEvent) event
    pointer.setFitWidth(iEvent.getMyGridSize())
    pointer.setFitHeight(iEvent.getMyGridSize())
    pointer.setX(FXProcessing.getXImageCoord(iEvent.getMyPos().at(0), pointer))
    pointer.setY(FXProcessing.getYImageCoord(iEvent.getMyPos().at(1), pointer))
})

entity.on(EventType.TRANSPARENT_MOUSE.getType(), { Event event ->
    TransparentMouseEvent tEvent = (TransparentMouseEvent) event
    pointer.setMouseTransparent(tEvent.getBool())
})

entity.on(EventType.VIEWVIS.getType(), { Event event ->
    ViewVisEvent visEvent = (ViewVisEvent) event
    pointer.setVisible(visEvent.getBool())
})

entity.on(EventType.CLICK.getType(), { Event event ->
    ClickEvent cEvent = (ClickEvent) event
    pointer.setOnMouseClicked( { MouseEvent e ->
        if (!cEvent.getIsGaming()) {
            pointer.requestFocus()
            if (e.getButton() == MouseButton.PRIMARY && cEvent.getMyMode()[0] == 0) {
                //might need try catch here
                System.out.println(cEvent.getMyBGType());
                cEvent.getMyBGType().reset()
                pointer.setImage(new Image(cEvent.getMyBGType()))
            }
            PubSub.getInstance().publish("ENTITY_PASS", new EntityPass(entity))
        }
        e.consume()
    })
})


entity.on(EventType.MOUSE_DRAG.getType(), { Event event ->
    MouseDragEvent dEvent = (MouseDragEvent) event
    if (dEvent.getIsGaming() == false && dEvent.getMyMode()[0] > 0) {
        pointer.setOnMousePressed({ MouseEvent e ->
            if (e.getButton().equals(MouseButton.SECONDARY)) {
                dEvent.setMyStartPos(e.getX(), e.getY())
                dEvent.setMyStartSize(pointer.getFitWidth(), pointer.getFitHeight())
            }
            e.consume()
        })
        pointer.setOnMouseDragged({ MouseEvent e ->
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                move(e)
            } else if (e.getButton().equals(MouseButton.SECONDARY)) {
                zoom(dEvent, e)
            }
            e.consume()
        })
    }
})


void zoom(MouseDragEvent dEvent, MouseEvent mouseEvent) {
    def change = (new Vector(mouseEvent.getX(), mouseEvent.getY())).subtract(dEvent.getMyStartPos())
    def fsize = change.add(dEvent.getMyStartSize())
    if (fsize.at(0) < 0) {
        fsize.at(0, 0.1)
    }
    if (fsize.at(1) < 0) {
        fsize.at(1, 0.1)
    }
    System.out.println(fsize)
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
    }
    pointer.setX(FXProcessing.getXImageCoord(xPos, pointer))
    pointer.setY(FXProcessing.getYImageCoord(yPos, pointer))
}