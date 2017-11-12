package engine.scripts.default

import engine.entities.Entity
import engine.scripts.IScript

/**
 * A Groovy Class Script that reverses an entity's direction
 */
class ReverseDirection implements IScript{

    @Override
    void execute(Entity entity) {
        entity.getVelocity().negate()
    }
}
