package engine.scripts

import java.util.Set

import engine.entities.Entity
import groovy.transform.Field

abstract class GroovyScript implements IScript {
	
	public Set getFields() {
		Set fields = this.getProperties().keySet()
		fields.remove("class")
		fields.remove("fields")
		return fields
	}
	
}