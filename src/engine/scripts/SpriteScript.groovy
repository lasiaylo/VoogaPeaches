package engine.scripts

import engine.entities.Entity

class SpriteScript extends GroovyScript {

	@Override
	public void execute(Entity entity) {
		println "executes!"
		
	}

	@Override
	public void start() {
		println "Initialized!"
	}

}
