package authoring.panels.tabbable;

import java.util.List;

import authoring.Panel;
import authoring.ScreenPosition;
import engine.entities.Entity;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**Displays the attributes associated with a particular Entity
 * @author lasia
 *
 */
public class AttributesPanel implements Panel {
	private HBox myHBox = new HBox();
	private List<Node> myChildren = myHBox.getChildren();
	private Entity myEntity;

	@Override
	public Region getRegion() {
		return myHBox;
	}
	
	@Override
	public ScreenPosition getPosition() {
		return ScreenPosition.TOP_RIGHT;
	}

	@Override
	public String title() {
		return "Properties";
	}
	
	/**Takes in an entity and displays its properties
	 * @param entity
	 */
	public void updateProperties(Entity entity) {
		myEntity = entity;
		
		addTransformProperty();
		addRenderProperty();
		addScriptProperties();
		addButton();
	}

	/**Displays the Transform properties of an entity
	 * 
	 */
	private void addTransformProperty() {
		
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
