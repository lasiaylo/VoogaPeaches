import engine.entities.Entity
import engine.events.Event
import engine.events.KeyPressEvent
import javafx.scene.input.KeyCode

{ Entity entity, Map<String, Object> bindings, Event event ->
    KeyPressEvent keyEvent = (KeyPressEvent) event
    def speed = bindings.get("speed")
    def maxspeed = bindings.get("maxspeed")
    def vx = entity.getProperty("vx")
    if (keyEvent.getKeyCode().equals(KeyCode.A)) {

        if (!(vx<=-maxspeed)) {
            println "left"
            if (vx>0){
                entity.setProperty("vx",-0.05)
            }
            entity.setProperty("vx", vx - speed)
        }
    }
    if (keyEvent.getKeyCode().equals(KeyCode.D)) {
        if (!(vx>=maxspeed)) {
            if (vx<0){
                entity.setProperty("vx",0.05)
            }
            entity.setProperty("vx", vx + speed)
        }
    }
}