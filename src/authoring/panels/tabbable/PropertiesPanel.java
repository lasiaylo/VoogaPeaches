package authoring.panels.tabbable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import authoring.Panel;
import authoring.PanelController;
import authoring.panels.attributes.CollapsePane;
import authoring.panels.attributes.ParameterButton;
import database.firebase.TrackableObject;
import engine.entities.Entity;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
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
public class PropertiesPanel implements Panel {
	private static final String SCRIPTS = "Scripts";
	private static final String PARAMETERS = "Parameters";
	private final String TITLE = "Properties";
	private Entity myEntity;
	private VBox myVBox;
	private Map<String, Object> myParameters;
	private Map<String, List<String>> myScripts;

	public PropertiesPanel() {
		myVBox = new VBox();
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

	public void updateProperties(String UID) throws GroovyInstantiationException {
		Entity entity = (Entity) TrackableObject.objectForUID(UID);
		updateProperties(entity);
	}
	/**Takes in an entity and displays its properties
	 * @param entity
	 * @throws GroovyInstantiationException 
	 */
	public void updateProperties(Entity entity) throws GroovyInstantiationException {
		myEntity = entity;
		//myVBox = new VBox();
		myVBox.getChildren().clear();
		myParameters = entity.getProperties();
		myScripts = (Map<String, List<String>>) myParameters.remove("scripts");
		updateView();
		myParameters.put("scripts", myScripts);

	}

	/**Updates the view of the AttributesPanel
	 * @throws GroovyInstantiationException
	 */
	public void updateView() throws GroovyInstantiationException {
		myVBox.getChildren().clear();
		makeParameters();
		makeScripts();
//		addButton();
	}

	private void makeParameters() throws GroovyInstantiationException {
		VBox parameterBox = new VBox();
		Node parameters = addMap(myParameters,false);
		Node button = new ParameterButton(myParameters, this).getNode();
		parameterBox.getChildren().add(parameters);
		parameterBox.getChildren().add(button);
		addPane(PARAMETERS, parameterBox);
	}

	private void makeScripts() throws GroovyInstantiationException {
		VBox scriptBox = new VBox();
		Node parameters = addMap(myScripts, true);
		Node button = new PanelController.ScriptButton(myScripts, this).getNode();
		scriptBox.getChildren().add(parameters);
		scriptBox.getChildren().add(button);
		addPane(SCRIPTS, scriptBox);
	}

	private void addPane(String title, Node pane) {
		TitledPane tPane = new TitledPane(title, pane);
		tPane.setAnimated(false);
		myVBox.getChildren().add(tPane);
	}

	/**Adds a collapse section that displays the map
	 * @param map
	 * @param collapse
	 * @throws GroovyInstantiationException
	 */
	private Node addMap(Map<String,?> map, boolean collapse) throws GroovyInstantiationException {
		System.out.println(map.toString() + "\n\n");
		CollapsePane pane = new CollapsePane(map, collapse);
		return pane.getNode();
	}
	
	/**Displays a button that allows users to add more scripts to an entity
	 * 
	 */
	private void addButton() {
		// TODO Auto-generated method stub
	}

	public Entity getEntity(){
		return myEntity;
	}

}
