package scripts

import database.filehelpers.FileDataFolders
import database.filehelpers.FileDataManager
import engine.entities.Entity
import engine.events.*
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

    ImageView pointer = new ImageView(new Image(datamanager.readFileData((String) bindings.get("image_path"))))
    pointer.setFitWidth((double) entity.getProperty("width"))
    pointer.setFitHeight((double) entity.getProperty("height"))
    pointer.setX(((double) entity.getProperty("x")))
    pointer.setY(((double) entity.getProperty("y")))
    originalPath = (String) bindings.get("image_path")
    entity.add(pointer)
    //boolean dragged = false

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
            entity.getProperty("scripts").get("imageScript").put("image_path", imgEvent.getPath())
            originalPath = imgEvent.getPath()
        })
    })

    entity.on(EventType.INITIAL_IMAGE.getType(), { Event call ->
        InitialImageEvent iEvent = (InitialImageEvent) call

        pointer.setFitWidth(entity.getProperty("width"))
        pointer.setFitHeight(entity.getProperty("height"))

        pointer.setX(iEvent.getMyPos().at(0))
        pointer.setY(iEvent.getMyPos().at(1))
        entity.setProperty("x", iEvent.getMyPos().at(0))
        entity.setProperty("y", iEvent.getMyPos().at(1))
        new ImageViewEvent(originalPath).fire(entity)
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
        if (!cEvent.getIsGaming()) {
            pointer.requestFocus()
            if(!entity.getProperties().getOrDefault("bg", false)) {
                PubSub.getInstance().publish("ENTITY_PASS", new EntityPass(entity))
            }
        }
        cEvent.getMouseEvent().consume()
    })

    entity.on(EventType.KEY_PRESS.getType(), { Event call ->
        KeyPressEvent kEvent = (KeyPressEvent) call
        if ((!kEvent.getIsGaming()) && kEvent.getKeyCode().equals(kEvent.getMyEvent().getCode())) {
                entity.getParent().remove(entity)
        }

    })

    entity.on(EventType.GAMING.getType(), { Event call ->
        GamingEvent kEvent = (GamingEvent) call
        pointer.addEventHandler(MouseEvent.MOUSE_CLICKED, {e -> new ClickEvent(kEvent.getIsGaming(), e).fire(entity)})
        pointer.addEventHandler(MouseEvent.MOUSE_DRAGGED, {e -> new MouseDragEvent(kEvent.getIsGaming(), e).fire(entity)})
        pointer.addEventHandler(KeyEvent.KEY_PRESSED, {e -> new KeyPressEvent(e, KeyCode.BACK_SPACE, kEvent.getIsGaming()).fire(entity)})
    })

    entity.on(EventType.MOUSE_DRAG.getType(), { Event call ->
        MouseDragEvent dEvent = (MouseDragEvent) call
        if (!dEvent.getIsGaming() && !entity.getProperties().getOrDefault("bg", false)) {
            dEvent.setMyStartPos(dEvent.getEvent().getX(), dEvent.getEvent().getY())
            dEvent.setMyStartSize(pointer.getFitWidth(), pointer.getFitHeight())
            pointer.setOnMouseDragged({ e ->
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    def xPos = e.getX()
                    def yPos = e.getY()
                    //LOL there is actually a bug here, if you try to drag over the right bound and lower bound
                    if (e.getX() < 0) {
                        xPos = pointer.getFitWidth()/2
                    }
                    if (e.getY() < 0) {
                        yPos = pointer.getFitHeight()/2
                    }
                    pointer.setX((xPos - pointer.getFitWidth()/2).doubleValue())
                    pointer.setY((yPos - pointer.getFitHeight()/2).doubleValue())
                    entity.setProperty("x", (xPos - pointer.getFitWidth()/2).doubleValue())
                    entity.setProperty("y", (yPos - pointer.getFitHeight()/2).doubleValue())
                } else if (e.getButton().equals(MouseButton.SECONDARY)) {
                    def change = (new Vector(e.getX(), e.getY())).subtract(dEvent.getMyStartPos())
                    def fsize = change.add(dEvent.getMyStartSize())
                    if (fsize.at(0) < 0) {
                        fsize.at(0, 0.1)
                    }
                    if (fsize.at(1) < 0) {
                        fsize.at(1, 0.1)
                    }
                    pointer.setFitWidth(fsize.at(0))
                    println("lower")
                    pointer.setFitHeight(fsize.at(1))
                    entity.setProperty("width", fsize.at(0));
                    entity.setProperty("height", fsize.at(1));

                }
                e.consume()
            })
            pointer.setOnMouseReleased({ e ->
                entity = entity.substitute()
            })
        }
        dEvent.getEvent().consume()
    })
}