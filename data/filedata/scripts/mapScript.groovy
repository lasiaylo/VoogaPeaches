
package scripts

import engine.entities.Entity
import engine.events.AddLayerEvent
import engine.events.Event
import engine.events.EventType
import engine.events.MapSetupEvent
import engine.events.MouseDragEvent
import engine.util.FXProcessing
import javafx.geometry.Pos
import javafx.scene.canvas.Canvas
import javafx.scene.input.DragEvent
import javafx.scene.input.MouseEvent
import javafx.scene.input.TransferMode
import javafx.scene.layout.StackPane
import util.math.num.Vector
import util.pubsub.PubSub
import util.pubsub.messages.BGMessage
import util.pubsub.messages.NonBGMessage


{ Entity entity, Map<String, Object> bindings, Event event = null ->
    entity = (Entity) entity
    canvas = new Canvas((double)entity.getProperty("mapwidth"), (double)entity.getProperty("mapheight"))
    stack = new StackPane()
    stack.getChildren().add(canvas)
    entity.add(stack)
    entity.on(EventType.MOUSE_DRAG.getType(), {Event call ->
        MouseDragEvent dEvent = (MouseDragEvent) call
        if (!dEvent.isGaming) {
            canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, { MouseEvent e ->
                dEvent.setMyStartPos(e.getX(), e.getY())
                e.consume()
            })
            canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, { MouseEvent e ->
                addBatch(e, dEvent.getMyStartPos(), (int) entity.getProperty("gridsize"))
                e.consume()
            })
        }
    })

    entity.on(EventType.ADDLAYER.getType(), { Event call ->
        AddLayerEvent addLayer = (AddLayerEvent) call
        stack.getChildren().add(addLayer.getLayerGroup())
        stack.setAlignment(addLayer.getLayerGroup(), Pos.TOP_LEFT)
    })
    entity.on(EventType.MAPSETUP.getType(), { Event call ->
        MapSetupEvent setup = (MapSetupEvent) call
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, { MouseEvent e ->
            PubSub.getInstance().publish("ADD_BG", new BGMessage(FXProcessing.getBGCenter(new Vector(e.getX(), e.getY()), (int)entity.getProperty("gridsize"))))
            e.consume()
        })
        stack.addEventHandler(DragEvent.DRAG_OVER, { DragEvent e ->
            if (e.getGestureSource() != stack && e.getDragboard().hasString()) {
                e.acceptTransferModes(TransferMode.COPY)
            }
            e.consume()
        })
        stack.addEventHandler(DragEvent.DRAG_DROPPED, { DragEvent e ->
            if (e.getDragboard().hasString()) {
                PubSub.getInstance().publish("ADD_NON_BG", new NonBGMessage(e.getDragboard().getString(),
                        new Vector(e.getX(), e.getY())))
            }
            e.setDropCompleted(true)
            e.consume()
        })
    })

    entity.on(EventType.RESET.getType(), { Event call ->
        stack.getChildren().remove(canvas)
        stack.getParent().getChildren().remove(stack)
        println("reset")
        canvas = null
        stack = null
        System.gc()
        System.runFinalization()
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
