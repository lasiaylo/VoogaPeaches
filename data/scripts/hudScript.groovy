package scripts

import engine.entities.Entity
import engine.events.AddLayerEvent
import engine.events.Event
import engine.events.EventType
import engine.events.MapSetupEvent
import engine.events.MouseDragEvent
import engine.util.FXProcessing
import javafx.geometry.Pos
import javafx.scene.Group
import javafx.scene.canvas.Canvas
import javafx.scene.input.DragEvent
import javafx.scene.input.MouseEvent
import javafx.scene.input.TransferMode
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import util.math.num.Vector
import util.pubsub.PubSub
import util.pubsub.messages.BGMessage
import util.pubsub.messages.NonBGMessage



{ Entity entity, Map<String, Object> bindings, Event event = null ->
    entity = (Entity) entity

    width = (double)entity.getProperty("cameraWidth")
    height = (double)entity.getProperty("cameraHeight")

    canvas = new Canvas(width, height)
    stack = new StackPane()
    rectange = new Rectangle(width,height)
    rectange.setFill(Color.TRANSPARENT);
    rectange.setStroke(Color.BLACK);

    stack.getChildren().add(rectange)
    stack.getChildren().add(canvas)

    entity.add(stack)

    entity.on(EventType.MOUSE_DRAG.getType(), {Event call ->
        MouseDragEvent dEvent = (MouseDragEvent) call
        if (!dEvent.isGaming) {
            canvas.setOnMousePressed({ MouseEvent e ->
                dEvent.setMyStartPos(e.getX(), e.getY())
                e.consume()
                println("start")
            })
            canvas.setOnMouseReleased({ MouseEvent e ->
                addBatch(e, dEvent.getMyStartPos(), (int) entity.getProperty("gridsize"))
                e.consume()
            })
        }
    })

//    entity.on(EventType.ADDLAYER.getType(), { Event call ->
//        AddLayerEvent addLayer = (AddLayerEvent) call
//        stack.getChildren().add(addLayer.getLayerGroup())
//        stack.setAlignment(addLayer.getLayerGroup(), Pos.TOP_LEFT)
//    })

    entity.on(EventType.MAPSETUP.getType(), { Event call ->
        MapSetupEvent setup = (MapSetupEvent) call
        canvas.setOnMouseClicked({ MouseEvent e ->
            PubSub.getInstance().publish("ADD_BG", new BGMessage(FXProcessing.getBGCenter(new Vector(e.getX(), e.getY()), (int)entity.getProperty("gridsize"))))
            e.consume()
        })
        stack.setOnDragOver({ DragEvent e ->
            if (e.getGestureSource() != stack && e.getDragboard().hasString()) {
                e.acceptTransferModes(TransferMode.COPY)
            }
            e.consume()
        })
        stack.setOnDragDropped({ DragEvent e ->
            if (e.getDragboard().hasString()) {
                PubSub.getInstance().publish("ADD_NON_BG", new NonBGMessage(e.getDragboard().getString(),
                        new Vector(e.getX(), e.getY())))
            }
            println("dropped")
            e.setDropCompleted(true)
            e.consume()
        })
    })
}

void addBatch(MouseEvent event, Vector start, int grid) {
    def end = new Vector(event.getX(), event.getY())
    def startC = FXProcessing.getBGCenter(start, grid)
    def endC = FXProcessing.getBGCenter(end, grid)
    for (def i = startC.at(0); i <= endC.at(0); i += grid) {
        for (def j = startC.at(1); j <= endC.at(1); j += grid) {
            PubSub.getInstance().publish("ADD_BG", new BGMessage(FXProcessing.getBGCenter(FXProcessing.getBGCenter(new Vector(i, j), grid), grid)))
        }
    }
    event.consume()
}
