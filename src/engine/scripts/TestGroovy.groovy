package engine.scripts

import engine.entities.Entity

class TestGroovy implements IScript {
	@Override
	public void execute(Entity entity) {
		println "Hello world!";
		println "Ayo, everything works!";
		
	}
}
