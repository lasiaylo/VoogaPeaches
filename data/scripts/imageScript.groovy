package scripts

import database.filehelpers.FileDataFolders
import database.filehelpers.FileDataManager
import engine.entities.Entity
import engine.events.ClickEvent
import engine.events.DragExitedEvent
import engine.events.Event
import engine.events.EventType
import engine.events.ImageViewEvent
import engine.events.InitialImageEvent
import engine.events.KeyPressEvent
import engine.events.MouseDragEvent
import engine.events.MousePressedEvent
import engine.events.TransparentMouseEvent
import engine.events.ViewVisEvent
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
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
    pointer.setFitWidth((double) entity.getProperty("width"))
    pointer.setFitHeight((double) entity.getProperty("height"))
    pointer.setX((double) entity.getProperty("x"))
    pointer.setY((double) entity.getProperty("y"))
    originalPath = (String) bindings.get("image_path")
    entity.add(pointer)
    pointer.setOnMouseClicked({e ->
        new ClickEvent(false, e).fire(entity)
    })
    boolean dragged = false

    pointer.setOnMouseDragged({e -> new MouseDragEvent(false, e).fire(entity)})
//    pointer.setOnMousePressed({e -> new MousePressedEvent(false, e).fire(entity)})
    pointer.setOnKeyPressed({e -> new KeyPressEvent(e).fire(entity)})
    //.setOnMousePressed({e -> new MousePressedEvent(false, e).fire(entity)})
    //pointer.setOnMouseReleased({e -> new DragExitedEvent(false, e).fire(entity)})

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
        entity.setProperty("x", iEvent.getMyPos().at(0))
        entity.setProperty("y", iEvent.getMyPos().at(1))
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

        println "click"
        if (!cEvent.getIsGaming()) {
            println "click"
            pointer.requestFocus()
            if(!entity.getProperties().getOrDefault("bg", false)) {
                PubSub.getInstance().publish("ENTITY_PASS", new EntityPass(entity))
            }
        }
        cEvent.getMouseEvent().consume()
    })

    entity.on(EventType.KEY_PRESS.getType(), { Event call ->
        KeyPressEvent kEvent = (KeyPressEvent) call
        if (kEvent.getIsGaming() == false && KeyCode.BACK_SPACE.equals(kEvent.getKeyCode())) {
                entity.getParent().remove(entity)
        }

    })

    entity.on(EventType.MOUSE_DRAG.getType(), { Event call ->
        MouseDragEvent dEvent = (MouseDragEvent) call
        if (!dEvent.getIsGaming() && !entity.getProperties().getOrDefault("bg", false)) {
            pointer.setOnMouseReleased({e -> new DragExitedEvent(false, e).fire(entity)})
            MouseEvent e = dEvent.getEvent()
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                move(e, entity)
            } else if (e.getButton().equals(MouseButton.SECONDARY)) {
                zoom(dEvent, e, entity)
            }
        }
        dEvent.getEvent().consume()
        dragged = true
    })

    entity.on(EventType.DRAG_EXITED.getType(), { Event call ->
        DragExitedEvent exitEvent = (DragExitedEvent) call
        if(dragged) {
            println "substitute"

            entity = entity.substitute()
        }
        exitEvent.getMouseEvent().consume()
    })

    entity.on(EventType.MOUSE_PRESS.getType(), { Event call ->
        MousePressedEvent dEvent = (MousePressedEvent) call
        dEvent.getMouseEvent().consume()
        //todo image scaling
    })

    if(!((boolean) getProperties().getOrDefault("bg", false))) {
        new InitialImageEvent(new Vector((double) entity.getProperty("width"), (double) entity.getProperty("height")),
                new Vector((double) entity.getProperty("x"), (double) entity.getProperty("y"))).fire(entity)
    }
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

    entity = entity.substitute()
}

void move(MouseEvent mouseEvent, Entity entity) {
    def xPos = mouseEvent.getX()
    def yPos = mouseEvent.getY()
    //LOL there is actually a bug here, if you try to drag over the right bound and lower bound
    if (mouseEvent.getX() < 0) {
        xPos = pointer.getFitWidth() / 2
    }
    if (mouseEvent.getY() < 0) {
        yPos = pointer.getFitHeight() / 2
    }
    pointer.setX(xPos - pointer.getFitWidth()/2)
    pointer.setY(yPos - pointer.getFitHeight()/2)

//    println xPos + " :: " + yPos
    entity.setProperty("x", xPos)
    entity.setProperty("y", yPos)
}