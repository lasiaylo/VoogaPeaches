package scripts

import database.filehelpers.FileDataFolders
import database.filehelpers.FileDataManager
import engine.entities.Entity
import engine.events.*
import javafx.event.EventHandler
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
    FileDataManager manager = new FileDataManager(FileDataFolders.IMAGES)
    ImageView view = null
    String originalPath = (String) bindings.get("image_path")

    Closure addEventListeners = {
        Double xStart = 0
        Double yStart = 0

        Double wStart = 0
        Double hStart = 0

        Double dx = 0
        Double dy = 0

        EventHandler drag_listener = { MouseEvent e ->
            Double xPos = e.getX()
            Double yPos = e.getY()

            if (e.getX() < 0)
                xPos = view.getFitWidth() / 2

            if (e.getY() < 0)
                yPos = view.getFitHeight() / 2

            view.setX((xPos - dx).doubleValue())
            view.setY((yPos - dy).doubleValue())
            entity.setProperty("x", (xPos - dx).doubleValue())
            entity.setProperty("y", (yPos - dy).doubleValue())

        }

        EventHandler resize_listener = { MouseEvent e ->
            view.setFitWidth(wStart + e.getX() - xStart)
            view.setFitHeight(hStart + e.getY() - yStart)

            entity.setProperty("width", wStart + e.getX() - xStart)
            entity.setProperty("height", hStart + e.getY() - yStart)
        }

        EventHandler switch_listener = { MouseEvent e ->
            xStart = e.getX()
            yStart = e.getY()

            wStart = view.getFitWidth()
            hStart = view.getFitHeight()

            dx = e.getX() - view.getX()
            dy = e.getY() - view.getY()

            if (e.getButton() == MouseButton.PRIMARY)
                view.setOnMouseDragged(drag_listener)

            else if (e.getButton() == MouseButton.SECONDARY)
                view.setOnMouseDragged(resize_listener)
        }

        entity.on(EventType.CLICK, { Event call ->
            ClickEvent cEvent = (ClickEvent) call
            if (!cEvent.getIsGaming()) {
                view.requestFocus()
                if (!entity.getProperties().getOrDefault("bg", false)) {
                    entity = entity.substitute()
                    PubSub.getInstance().publish("ENTITY_PASS", new EntityPass(entity))
                }
            }
            cEvent.getMouseEvent().consume()
        })

        view.addEventHandler(MouseEvent.MOUSE_PRESSED, switch_listener)
    }

    Closure routeEvents = {
        view.addEventHandler(MouseEvent.MOUSE_CLICKED, { e ->
            new ClickEvent(false, e).fire(entity)
        })

        view.addEventHandler(MouseEvent.MOUSE_DRAGGED, { e ->
            new MouseDragEvent(false, e).fire(entity)
        })

        view.addEventHandler(KeyEvent.KEY_PRESSED, { e ->
            new KeyPressEvent(e, KeyCode.BACK_SPACE, false).fire(entity)
        })
    }

    Closure initImage = {
        view = new ImageView(new Image(manager.readFileData((String) bindings.get("image_path"))))
        view.setFitWidth((double) entity.getProperty("width"))
        view.setFitHeight((double) entity.getProperty("height"))
        view.setX(((double) entity.getProperty("x")))
        view.setY(((double) entity.getProperty("y")))
        originalPath = (String) bindings.get("image_path")
        entity.add(view)

        routeEvents()
        addEventListeners()

        entity.on(EventType.INITIAL_IMAGE, { Event e ->
            InitialImageEvent iEvent = (InitialImageEvent) e
            view.setFitWidth(iEvent.getMyGridSize().at(0))
            entity.setProperty("width", iEvent.getMyGridSize().at(0))
            view.setFitHeight(iEvent.getMyGridSize().at(1))
            entity.setProperty("height", iEvent.getMyGridSize().at(1))
            view.setX(iEvent.getMyPos().at(0))
            view.setY(iEvent.getMyPos().at(1))
            entity.setProperty("x", iEvent.getMyPos().at(0))
            entity.setProperty("y", iEvent.getMyPos().at(1))
            println "initial image"
//            new ImageViewEvent(originalPath).fire(entity)
        })
    }()

    entity.on(EventType.IMAGE_VIEW, { Event e ->
        ImageViewEvent imageViewEvent = (ImageViewEvent) e

        view.setImage(new Image(manager.readFileData((String) imageViewEvent.getPath())))
        Map scriptMap = ((Map) entity.getProperty("scripts"))

        List imagePathList = scriptMap.keySet().stream().filter({ String name ->
            name.equals("image_script")
        }).collect(Collectors.toList())

        imagePathList.forEach({ String path ->
            ((Map) entity.getProperty("scripts")).get("image_script").put("image_path", imageViewEvent.getPath())
            originalPath = imageViewEvent.getPath()
        })
    })
}