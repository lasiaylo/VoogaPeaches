package authoring.panels.tabbable;

import java.util.List;
import java.util.Map;
import authoring.Panel;
import authoring.panels.attributes.CollapsePane;
import engine.entities.Entity;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import util.ErrorDisplay;
import util.exceptions.GroovyInstantiationException;
import util.pubsub.PubSub;
import util.pubsub.messages.EntityPass;

/**Displays the attributes associated with a particular Entity
 * @author lasia
 *
 */
public class AttributesPanel implements Panel {
	private static final String SCRIPTS = "Scripts";
	private static final String PARAMETERS = "Parameters";
	private final String TITLE = "Properties";
	private VBox myVBox;
	private Map<String, Object> myParameters;
	private Map<String, List<String>> myScripts;

	public AttributesPanel() {
		PubSub.getInstance().subscribe("ENTITY_PASS", e -> {
			EntityPass ePass = (EntityPass) e;
			try {
				this.updateProperties(ePass.getEntity());
			} catch (GroovyInstantiationException exception) {
				new ErrorDisplay("Groovy Error",
						"You're trying to set something incorrectly, bro! Not Groovy!").displayError();
			}
		});
	}

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
		myScripts = (Map<String, List<String>>) entity.getProperty("scripts");
		myParameters.remove("scripts");
		updateView();
		
	}

	/**Updates the view of the AttributesPanel
	 * @throws GroovyInstantiationException
	 */
	private void updateView() throws GroovyInstantiationException {
		addMap(myParameters, PARAMETERS, false);
		addMap(myScripts, SCRIPTS, true);
//		addButton();
	}


	/**Adds a collapse section that displays the map
	 * @param map
	 * @param title
	 * @param collapse
	 * @throws GroovyInstantiationException
	 */
	private void addMap(Map<String,?> map, String title, boolean collapse) throws GroovyInstantiationException {
		CollapsePane pane = new CollapsePane(map, title, collapse);
		myVBox.getChildren().add(pane.getNode());
	}
	
	/**Displays a button that allows users to add more scripts to an entity
	 * 
	 */
	private void addButton() {
		// TODO Auto-generated method stub
	}

}
