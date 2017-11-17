package engine.scripts.defaults

import engine.entities.Entity

/**
 * A Groovy Class Script that reverses an entity's direction
 */
class ReverseDirection extends GroovyScript {

    @Override
    void start() {

    }

    @Override
    void execute(Entity entity) {
        entity.getVelocity().negate()
    }
}