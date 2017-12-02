package authoring.panels.tabbable;

import authoring.Panel;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

/**Displays the properties associated with a particular Entity
 * @author lasia
 *
 */
public class PropertiesPanel implements Panel {
	private HBox myHBox;
	private Entity myEntity;

	private Pane myPane;

	public PropertiesPanel() {
		myPane = new Pane();
		myPane.getStyleClass().add("panel");
	}
	
	@Override
	public Region getRegion() {
		return myPane;
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
		// TODO Auto-generated method stub
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
