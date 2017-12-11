package scripts

import engine.entities.Entity
import engine.events.AddLayerEvent
import engine.events.Event
import engine.events.EventType
import engine.events.MapSetupEvent
import engine.events.MouseDragEvent
import engine.util.FXProcessing
import javafx.geometry.Pos
import javafx.scene.Camera
import javafx.scene.Group
import javafx.scene.ParallelCamera
import javafx.scene.canvas.Canvas
import javafx.scene.input.DragEvent
import javafx.scene.input.MouseEvent
import javafx.scene.input.TransferMode
import javafx.scene.layout.StackPane
import util.math.num.Vector
import util.pubsub.PubSub
import util.pubsub.messages.BGMessage
import util.pubsub.messages.NonBGMessage

//lmao idk what I'm doing

{ Entity entity, Map<String, Object> bindings, Event event = null ->
    entity = (Entity) entity
    canvas = new Canvas((double)entity.get
            Property("cameraWidth"), (double)entity.getProperty("cameraHeight"))
    stack = new StackPane()
    camera = new ParallelCamera()
    entity.add(camera);

    entity.on(EventType.CAMERA.getType(), {Event call ->
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


}
