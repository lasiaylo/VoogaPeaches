package engine.scripts;

import java.util.List;

import engine.entities.Entity;
import javafx.scene.Node;

/**Allows entities to take in an input
 * 
 *
 */
public class InputCondition extends Conditional {

	public InputCondition(List<IScript> scripts) {
		super(scripts);
	}
	
	public InputCondition() {
		super();
	}
	
	@Override
	public void execute(Entity entity) {
		// TODO Auto-generated method stub
		
	}
}
