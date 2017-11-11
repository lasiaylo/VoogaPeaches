package engine.scripts;

import java.util.ArrayList;
import java.util.List;

import engine.entities.Entity;
import javafx.scene.Node;

/**Allows entities to take in an input
 * 
 *
 */
public class InputConditional extends Conditional {
	
	/** Creates a new InputConditional
	 * @param List of Scripts to run when user inputs a particular command
	 */
	public InputConditional(List<IScript> scripts) {
		super(null,scripts);
	}
	
	/** Creates a new InputConditional
	 * 
	 */
	public InputConditional() {
		this(new ArrayList<IScript>());
	}
	
}
