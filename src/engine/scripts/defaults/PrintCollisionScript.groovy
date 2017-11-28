package engine.scripts.defaults

import engine.entities.Entity
import engine.managers.CollisionManager
import engine.managers.HitBoxCheck

class PrintCollisionScript extends GroovyScript{
    String message = "hell yeah"

    PrintCollisionScript() {
        super()
    }

    @Override
    void start() {

    }

    @Override
    void execute(Entity entity) {
        System.out.println(message)
    }
}
