package engine.scripts.defaults

import engine.entities.Entity
import util.math.num.Vector

class StopMovement extends GroovyScript {
    @Override
    void start() {

    }

    @Override
    void execute(Entity entity) {
        Vector velocity = new Vector(0, 0);
        Vector acceleration = new Vector(0, 0);

        entity.getTransform().setVelocity(velocity)
        entity.getTransform().setAcceleration(acceleration)
    }
}
