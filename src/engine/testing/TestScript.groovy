package engine.testing

import engine.entities.Entity
import engine.scripts.IScript
import engine.scripts.Script

class TestScript implements IScript{

    @Override
    def execute(Entity entity) {
        println 'Hello World'
    }
}
