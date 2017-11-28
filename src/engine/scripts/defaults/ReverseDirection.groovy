package engine.scripts.defaults

import engine.entities.Entity
import engine.entities.Transform
import util.math.num.Vector;

/**
 * A Groovy Class Script that reverses an entity's direction
 */
class ReverseDirection extends GroovyScript {

    @Override
    void start() {

    }

    @Override
    void execute(Entity entity) {
        Transform transform = entity.getTransform()
        transform.setVelocity(transform.getVelocity().negate())
        transform.setAcceleration(entity.getTransform().getAcceleration().negate())
    }
}