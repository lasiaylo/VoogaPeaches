package filedata.scripts

import engine.entities.Entity
import javafx.scene.Node
import javafx.scene.shape.Rectangle

{ Entity entity, Map<String, Object> bindings ->
    Random r = new Random()
    HashSet<Rectangle> clouds = new HashSet<>()

    Closure runnable = {
        while (true) {
            Rectangle rectangle = new Rectangle(850, r.nextInt(200), 20, 20)
            clouds.add(rectangle)
            entity.add(rectangle)
            Thread.sleep(1000 + r.nextInt(1000))
        }
    }

    Closure update = {
        while (true) {
            Rectangle c = null
            Iterator<Rectangle> iterator = clouds.iterator()
            while ((c = iterator.next()) != null)
                if (c.getX() < 0) {
                    iterator.remove()
                    entity.getNodes().getChildren().remove(c)
                } else
                    c.setX(c.getX() - 2)
            Thread.sleep(10)
        }
    }

    new Thread(runnable).start()
    new Thread(update).start()
}