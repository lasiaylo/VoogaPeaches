package scriptdata

import database.filehelpers.FileDataFolders
import database.filehelpers.FileDataManager
import engine.entities.Entity
import engine.events.ClickEvent
import engine.events.Event
import engine.events.EventType
import engine.events.ImageViewEvent
import engine.events.InitialImageEvent
import engine.events.KeyPressEvent
import engine.events.MouseDragEvent
import engine.events.TransparentMouseEvent
import engine.events.ViewVisEvent
import engine.util.FXProcessing
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import util.math.num.Vector
import util.pubsub.PubSub
import util.pubsub.messages.EntityPass

import java.util.stream.Collectors

{ Entity entity, Map<String, Object> bindings, Event event = null ->
    entity = (Entity) entity
    datamanager = new FileDataManager(FileDataFolders.IMAGES)
    pointer = new ImageView(new Image(datamanager.readFileData((String) bindings.get("image_path"))))
    pointer.setFitWidth(entity.getProperty("width"))
    pointer.setFitHeight(entity.getProperty("height"));
    pointer.setX((double) entity.getProperty("x"))
    pointer.setY((double) entity.getProperty("y"))
    originalPath = (String) bindings.get("image_path")
    entity.add(pointer)

    entity.on(EventType.IMAGE_VIEW.getType(), { Event call ->
        ImageViewEvent imgEvent = (ImageViewEvent) call

        pointer.setImage(new Image(datamanager.readFileData((String) imgEvent.getPath())))
        scriptMap = ((Map) entity.getProperty("scripts"))

        imagePathList = scriptMap.keySet().stream().filter({String name ->
            name.equals("imageScript")
        }).filter({ String name ->
            scriptMap.get(name).get("image_path").equals(originalPath)
        }).collect(Collectors.toList())

        imagePathList.forEach({ String path ->
            entity.getProperty("scripts").get(path).put("image_path", imgEvent.getPath())
            originalPath = path
        })
    })

    entity.on(EventType.INITIAL_IMAGE.getType(), { Event call ->
        InitialImageEvent iEvent = (InitialImageEvent) call
        pointer.setFitWidth(iEvent.getMyGridSize().at(0))
        entity.setProperty("width", iEvent.getMyGridSize().at(0))
        pointer.setFitHeight(iEvent.getMyGridSize().at(1))
        entity.setProperty("height", iEvent.getMyGridSize().at(1))
        pointer.setX(iEvent.getMyPos().at(0))
        pointer.setY(iEvent.getMyPos().at(1))
        entity.setProperty("x", iEvent.getMyPos().at(0));
        entity.setProperty("y", iEvent.getMyPos().at(1));
    })

    entity.on(EventType.TRANSPARENT_MOUSE.getType(), { Event call ->
        TransparentMouseEvent tEvent = (TransparentMouseEvent) call
        pointer.setMouseTransparent(tEvent.getBool())
    })

    entity.on(EventType.VIEWVIS.getType(), { Event call ->
        ViewVisEvent visEvent = (ViewVisEvent) call
        pointer.setVisible(visEvent.getBool())
    })

    entity.on(EventType.CLICK.getType(), { Event call ->
        ClickEvent cEvent = (ClickEvent) call
        pointer.setOnMouseClicked( { MouseEvent e ->
            if (!cEvent.getIsGaming()) {
                pointer.requestFocus()
                if(!entity.getProperties().getOrDefault("bg", false)) {
                    PubSub.getInstance().publish("ENTITY_PASS", new EntityPass(entity))
                }            }
            e.consume()
        })
    })

    entity.on(EventType.KEY_PRESS.getType(), { Event call ->
        KeyPressEvent kEvent = (KeyPressEvent) call
        pointer.setOnKeyPressed( { KeyEvent e ->
            if (kEvent.getIsGaming() == false && e.getCode().equals(kEvent.getKeyCode())) {
                entity.getParent().remove(entity)
            }
            e.consume()
        })
    })


    entity.on(EventType.MOUSE_DRAG.getType(), { Event call ->
        MouseDragEvent dEvent = (MouseDragEvent) call
        if (dEvent.getIsGaming() == false && !entity.getProperties().getOrDefault("bg", false)) {
            pointer.setOnMousePressed({ MouseEvent e ->
                if (e.getButton().equals(MouseButton.SECONDARY)) {
                    dEvent.setMyStartPos(e.getX(), e.getY())
                    dEvent.setMyStartSize(pointer.getFitWidth(), pointer.getFitHeight())
                }
                e.consume()
            })
            pointer.setOnMouseDragged({ MouseEvent e ->
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    move(e, entity)
                } else if (e.getButton().equals(MouseButton.SECONDARY)) {
                    zoom(dEvent, e, entity)
                }
                e.consume()
            })
        }
    })
}

void zoom(MouseDragEvent dEvent, MouseEvent mouseEvent, Entity entity) {
    def change = (new Vector(mouseEvent.getX(), mouseEvent.getY())).subtract(dEvent.getMyStartPos())
    def fsize = change.add(dEvent.getMyStartSize())
    if (fsize.at(0) < 0) {
        fsize.at(0, 0.1)
    }
    if (fsize.at(1) < 0) {
        fsize.at(1, 0.1)
    }
    pointer.setFitWidth(fsize.at(0))
    pointer.setFitHeight(fsize.at(1))
    entity.setProperty("width", fsize.at(0));
    entity.setProperty("height", fsize.at(1));
}

void move(MouseEvent mouseEvent, Entity entity) {
    def xPos = mouseEvent.getX()
    def yPos = mouseEvent.getY()
    //LOL there is actually a bug here, if you try to drag over the right bound and lower bound
    if (mouseEvent.getX() < pointer.getFitWidth() / 2) {
        xPos = pointer.getFitWidth() / 2
    }
    if (mouseEvent.getY() < pointer.getFitHeight() / 2) {
        yPos = pointer.getFitHeight() / 2
    }
    pointer.setX(xPos)
    pointer.setY(yPos)

    println xPos + " :: " + yPos
    entity.setProperty("x", xPos);
    entity.setProperty("y", yPos);
}