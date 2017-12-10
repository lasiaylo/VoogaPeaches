package scripts

import engine.entities.Entity
import engine.events.AddLayerEvent
import engine.events.Event
import engine.events.EventType
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


    entity.on(EventType.MOUSE_DRAG.getType(), {Event call ->
        MouseDragEvent dEvent = (MouseDragEvent) call
        if (!dEvent.isGaming) {
            canvas.setOnMousePressed({ MouseEvent e ->
                dEvent.setMyStartPos(e.getX(), e.getY())
                e.consume()
            })
            canvas.setOnMouseReleased({ MouseEvent e ->
                addBatch(e, dEvent.getMyStartPos(), (int) entity.getProperty("gridSize"))
            })
        }
    })

    entity.on(EventType.ADDLAYER.getType(), { Event call ->
        AddLayerEvent addLayer = (AddLayerEvent) call
        stack.getChildren().add(addLayer.getLayerGroup())
        stack.setAlignment(addLayer.getLayerGroup(), Pos.TOP_LEFT)
    })
    entity.getNodes().getChildren().add(stack)

            {
                canvas.setOnMouseClicked({ MouseEvent e ->
                    PubSub.getInstance().publish("ADD_BG", new BGMessage(FXProcessing.getBGCenter(new Vector(e.getX(), e.getY()), (int)entity.getProperty("gridSize"))))
                    e.consume()
                })
            }

            {
                stack.setOnDragOver({ DragEvent e ->
                    if (e.getGestureSource() != stack && e.getDragboard().hasString()) {
                        e.acceptTransferModes(TransferMode.COPY)
                    }
                    e.consume()
                })
            }

            {
                stack.setOnDragDropped({ DragEvent e ->
                    if (e.getDragboard().hasString()) {
                        PubSub.getInstance().publish("ADD_NON_BG", new NonBGMessage(e.getDragboard().getString(),
                                new Vector(e.getX(), e.getY())))
                    }
                    e.setDropCompleted(true)
                    e.consume()
                })
            }

}