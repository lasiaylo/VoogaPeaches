package authoring.panels.tabbable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import authoring.Panel;
import authoring.panels.attributes.Attribute;
import engine.entities.Entity;
import engine.scripts.IScript;
import engine.scripts.Script;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import util.exceptions.GroovyInstantiationException;

/**Displays the attributes associated with a particular Entity
 * @author lasia
 *
 */
public class AttributesPanel implements Panel {
	private VBox myVBox;
	private Entity myEntity;

	@Override
	public Region getRegion() {
		return myVBox;
	}
	
	@Override
	public String title() {
		return "Properties";
	}
	
	/**Takes in an entity and displays its properties
	 * @param entity
	 * @throws GroovyInstantiationException 
	 */
	public void updateProperties(Entity entity) throws GroovyInstantiationException {
		myVBox = new VBox();
		myEntity = entity;
		Set<String> methods = new HashSet<>(Arrays.asList("Position","Velocity","Acceleration"));
		addAttribute(myEntity.getTransform(),"Transform", methods);
		System.out.println(myEntity.getScripts());
		for (IScript s : myEntity.getScripts()) {
			Script script = (Script) s;
			addAttribute(script.getScript(), "Script", script.getFields());
		}
//		addButton();
	}


	/**Adds and displays the attribute of an entity
	 * @param object
	 * @param title
	 * @param methods
	 * @throws GroovyInstantiationException
	 */
	private void addAttribute(Object object, String title, Set<String> methods) throws GroovyInstantiationException {
		Attribute at = new Attribute(object, title, methods);
		myVBox.getChildren().add(at.getNode());
	}
	
	
	/**Displays a button that allows users to add more scripts to an entity
	 * 
	 */
	private void addButton() {
		// TODO Auto-generated method stub
	}

}
