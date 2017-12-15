import engine.entities.Entity
import engine.events.Event

import javafx.animation.Timeline
import javafx.animation.KeyFrame
import javafx.animation.Animation
import javafx.util.Duration


{ Entity entity, Map<String, Object> bindings, Event event = null ->
    Timeline timeline = new Timeline(
            new KeyFrame(
                    Duration.millis(250),
                    {frame -> 1.times {
                        double i = Math.random() - 0.5
                        double x = (double) entity.getProperty("x")
                        double y = (double) entity.getProperty("y")
                        entity.setProperty("x", x + (i*20))
                        i = Math.random() - 0.5
                        entity.setProperty("y", y + (i*20))
                    }}
            )
    )
    timeline.setCycleCount(Animation.INDEFINITE)
    timeline.play()

}