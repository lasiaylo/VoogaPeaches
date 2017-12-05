package engine.scripts.defaults

import java.util.Set

import engine.entities.Entity
import engine.scripts.IScript
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
	public abstract void start()
	
	/**Allows front end to retrieve all the unmodified specified fields. 
	 * 
	 * @return fields defined within this script
	 */
	public Set<String> getFields() {
		Set fields = getProperties().keySet()
		fields.remove("class")
		fields.remove("fields")
		
		return fields;
	}
}