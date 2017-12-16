package filedata.scripts

import database.ObjectFactory
import database.jsonhelpers.JSONDataFolders
import engine.entities.Entity
import engine.events.Event
import javafx.animation.Animation
import javafx.animation.KeyFrame
import javafx.animation.PauseTransition
import javafx.animation.Timeline
import javafx.util.Duration

{ Entity entity, Map<String, Object> bindings, Event event = null ->

    Timeline timeline = new Timeline(
            new KeyFrame(
                    Duration.millis(1000),
                    {frame -> 1.times {
                        if((boolean)entity.getProperty("firing")){
                            double vx = (double) entity.getProperty("vx")
                            double vy = (double) entity.getProperty("vy")
                            if(vx != 0 || vy != 0){
                                ObjectFactory o = new ObjectFactory("orb.json",JSONDataFolders.ENTITY_BLUEPRINT)
                                Entity i = o.newObject()
                                entity.parent.add(i)
                                i.setProperty("vx", 3 * vx)
                                i.setProperty("vy", 3 * vy)
                                i.setProperty("x",entity.getProperty("x"))
                                i.setProperty("y", entity.getProperty("y"))

                                PauseTransition pt = new PauseTransition(Duration.seconds(1))
                                pt.setOnFinished({e -> entity.parent.remove(i)})
                                pt.play()
                            }
                        }
                    }}
            )
    )
    timeline.setCycleCount(Animation.INDEFINITE)
    timeline.play()


}
