package engine.scripts

import java.util.Set

import engine.entities.Entity
import groovy.transform.Field

/**Provides interface for scripts
 * @author lasia
 *
 */
abstract class GroovyScript implements IScript {

//	If you want a field to be able to be changed via get/set, declare it with no modifiers
//	Otherwise, declare with "private"
	
	public GroovyScript() {
		start()
	}
	
	/**Initializes state for the script
	 * 
	 */
	public abstract void start();
	
	public Set getFields() {
		Set fields = this.getProperties().keySet()
		fields.remove("class")
		fields.remove("fields")
		return fields
	}
}