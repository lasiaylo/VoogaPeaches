package authoring.panels.tabbable;

import java.util.List;
import authoring.Panel;
import engine.entities.Entity;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import util.exceptions.GroovyInstantiationException;

/**Displays the attributes associated with a particular Entity
 * @author lasia
 *
 */
public class AttributesPanel implements Panel {
	private HBox myHBox;
	private Entity myEntity;

	@Override
	public Region getRegion() {
		return myHBox;
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
		myHBox = new HBox();
		myEntity = entity;
		addTransformProperty();
//		addRenderProperty();
//		addScriptProperties();
//		addButton();
	}

	/**Displays the Transform properties of an entity
	 * @throws GroovyInstantiationException 
	 * 
	 */
	private void addTransformProperty() throws GroovyInstantiationException {
		TransformAttribute transform = new TransformAttribute(myEntity);
		myHBox.getChildren().add(transform.getNode());
	}
	
	/**Displays the Render properties of an entity
	 * 
	 */
	private void addRenderProperty() {
		// TODO Auto-generated method stub
		
	}
	
	/**Displays the individual scripts of an entity
	 * 
	 */
	private void addScriptProperties() {
		// TODO Auto-generated method stub
	}
	
	/**Displays a button that allows users to add more scripts to an entity
	 * 
	 */
	private void addButton() {
		// TODO Auto-generated method stub
	}

}
