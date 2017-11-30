package authoring.panels.tabbable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import authoring.Panel;
import authoring.panels.attributes.VectorField;
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
		addTest();
//		addTransformProperty();
//		addRenderProperty();
//		addScriptProperties();
//		addButton();
	}

	private void addTest() throws GroovyInstantiationException {
		Set<String> methods = new HashSet<>(Arrays.asList("Position"));
		Attribute a = new Attribute(myEntity.getTransform(),"Transform",methods);
		myHBox.getChildren().add(a.getPane());
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
