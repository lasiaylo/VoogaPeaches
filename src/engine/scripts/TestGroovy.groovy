package engine.scripts

import java.util.Set

import engine.entities.Entity
import groovy.transform.Field

class TestGroovy implements IScript {
	int dude =3
	int ight = 23
	@Override
	public void execute(Entity entity) {
		println "Ayo, everything works!"
	}
	
	@Override
	public Set getFields() {
		Set fields = this.getProperties().keySet()
		fields.remove("class")
		fields.remove("fields")
		return fields
	}
	
}