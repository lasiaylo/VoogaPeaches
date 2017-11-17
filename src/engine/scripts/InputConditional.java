package engine.scripts;

import java.util.ArrayList;
import java.util.List;

import engine.entities.Entity;
import engine.managers.InputManager;
import javafx.scene.Node;

/**Allows entities to take in an input
 * 
 *
 */
public class InputConditional extends Conditional {
	
//	TODO: Give InputConditional a InputManager
	/** Creates a new InputConditional
	 * @param List of Scripts to run when user inputs a particular command
	 */
	public InputConditional(List<IScript> scripts) {
		super(InputManager.getInstance(),scripts);
	}
	
	/** Creates a new InputConditional
	 * 
	 */
	public InputConditional() {
		this(new ArrayList<IScript>());
	}
	
}
