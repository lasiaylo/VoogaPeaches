package filedata.scripts

import engine.entities.Entity
import engine.events.Event
import engine.events.EventType
import engine.events.KeyPressEvent
import javafx.scene.input.KeyCode
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.scene.shape.Rectangle

{ Entity entity, Map<String, Object> bindings ->
    Circle circle = new Circle(10)
    entity.add(circle)

    circle.setCenterX(30)
    double t = 8.0
    double d = 300
    int counter = 1000

    Random r = new Random()
    HashSet<Rectangle> clouds = new HashSet<>()

    ArrayList<Rectangle> list = new ArrayList<>()

    entity.on(EventType.KEY_PRESS, { Event e ->
        e = (KeyPressEvent) e
        System.out.print(e)
        switch (e.keyCode) {
            case KeyCode.W:
                System.out.println(e)
                t = -8.0
                break
        }
    })

    entity.on(EventType.TICK, {
        if (t < 8.0)
            t += 0.8

        circle.setCenterY(226 + t * t)

        Rectangle c = null
        Iterator<Rectangle> iterator = clouds.iterator()
        while (iterator.hasNext()) {
            c = iterator.next()

            if (circle.intersects(c.getBoundsInLocal()))
                println "Lost!!!"

            if (c.getX() < 0) {
                iterator.remove()
                entity.getNodes().getChildren().remove(c)
                println "removed"
            } else
                c.setX(c.getX() - 8)
        }

        if (counter++ > d) {
            Rectangle rectangle = new Rectangle(850, 280, 20, 20)
            clouds.add(rectangle)
            entity.add(rectangle)
            Thread.sleep(1000 + r.nextInt(1000))
            counter = 0
            d -= 10
        }
    })
}