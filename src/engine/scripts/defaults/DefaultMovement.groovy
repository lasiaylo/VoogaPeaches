package engine.scripts.defaults

import engine.entities.EngineLoop
import engine.entities.Entity
import engine.entities.Transform
import util.math.num.Vector

class DefaultMovement extends GroovyScript {
    @Override
    void start() {

    }

    @Override
    void execute(Entity entity) {
        Transform transform = entity.getTransform()
        Vector position = transform.getPosition()
        Vector velocity = transform.getVelocity()
        Vector acceleration = transform.getAcceleration()
        double period = EngineLoop.FRAME_PERIOD / 1000

        Vector newPosition = position.add(acceleration.multiply(0.5 * Math.pow(period, 2)).add(velocity.multiply(period)))
        Vector newVelocity = velocity.add(acceleration.multiply(period))

        position = null
        velocity = null

        transform.setPosition(newPosition)
        transform.setVelocity(newVelocity)

        entity.getRender().displayUpdate(entity.getTransform());
    }
}
