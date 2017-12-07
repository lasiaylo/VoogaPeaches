package authoring.panels.tabbable;

import java.util.List;
import java.util.Map;
import authoring.Panel;
import engine.entities.Entity;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import util.exceptions.GroovyInstantiationException;

/**Displays the attributes associated with a particular Entity
 * @author lasia
 *
 */
public class AttributesPanel implements Panel {
	private final String TITLE = "Properties";
	private VBox myVBox;
	private Map<String, Object> myParameters;
	private Map<String, List<String>> myScripts;

	@Override
	public Region getRegion() {
		return myVBox;
	}
	
	@Override
	public String title() {
		return TITLE;
	}
	
	/**Takes in an entity and displays its properties
	 * @param entity
	 * @throws GroovyInstantiationException 
	 */
	public void updateProperties(Entity entity) throws GroovyInstantiationException {
		myVBox = new VBox();
		myParameters = entity.getProperties();
		myParameters.remove("scripts");
		
		myScripts = (Map<String, List<String>>) entity.getProperty("scripts");
		
		addMap(myParameters);
		addMap(myScripts);
//		addButton();
	}


	private void addMap(Map<String,?> map) throws GroovyInstantiationException {
	
	}
	
	/**Displays a button that allows users to add more scripts to an entity
	 * 
	 */
	private void addButton() {
		// TODO Auto-generated method stub
	}

}
