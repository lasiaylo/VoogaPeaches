package engine.scripts.defaults

import engine.entities.Entity
import util.math.num.Vector

class XReverse extends GroovyScript{
    @Override
    void start() {

    }

    @Override
    void execute(Entity entity) {
//        System.out.println("x reverse")
        Vector currentVel = entity.getTransform().getVelocity()
        Vector currentAccel = entity.getTransform().getAcceleration()


        Vector newVel = new Vector(-1 * currentVel.at(0), currentVel.at(1));
        Vector newAccel = new Vector(-1 * currentAccel.at(0), currentAccel.at(1))

        currentVel = null
        currentAccel = null
        entity.getTransform().setVelocity(newVel)
        entity.getTransform().setAcceleration(newAccel)
    }
}
