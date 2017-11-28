package engine.scripts.defaults

import engine.entities.Entity
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
        entity.getTransform().setPosition(new Vector(5*Math.random(), 5*Math.random()))
    }
}