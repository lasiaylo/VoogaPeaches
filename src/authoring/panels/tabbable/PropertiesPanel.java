package authoring.panels.tabbable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import authoring.Panel;
import authoring.PanelController;
import authoring.buttons.CustomButton;
import authoring.buttons.strategies.EntitySave;
import authoring.panels.attributes.CollapsePane;
import authoring.panels.attributes.ParameterButton;
import authoring.panels.attributes.ScriptButton;
import database.firebase.TrackableObject;
import engine.entities.Entity;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
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
	private Map<String, Map<String, Object>> myScripts;

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
		//myScripts = (Map<String, Map<String, Object>>) myParameters.remove("scripts");
		updateView();
		myParameters.put("scripts", myScripts);

	}

	/**Updates the view of the AttributesPanel
	 * @throws GroovyInstantiationException
	 */
	public void updateView() throws GroovyInstantiationException {
		myVBox.getChildren().clear();
		addChildren(myVBox, makeParameters(myParameters),
				makeScripts());
		addButton();
	}

	private Node makeParameters(Map<String, Object> parameterMap) throws GroovyInstantiationException {
		VBox parameterBox = new VBox();
		Node parameters = addMap(parameterMap,false);
		Node button = new ParameterButton(parameterMap, this).getNode();
		addChildren(parameterBox, parameters, button);
		return addPane(PARAMETERS, parameterBox);
	}

	private void addChildren(Pane pane, Node...nodes) {
			pane.getChildren().addAll(nodes);
	}

	private Node makeScripts() throws GroovyInstantiationException {
		VBox scriptBox = new VBox();

		for (String s : myScripts.keySet()){

			Map<String, Object> event = myScripts.get(s);
			List<String> actions = (List<String>) myScripts.get(s).get("actions");
			Map<String, Object>  bindings = (Map<String, Object>) myScripts.get(s).get("bindings");
			addChildren(scriptBox,makeParameters(bindings));
//			addChildren(scriptBox, title);
		}
//		Node parameters = addMap(myScripts, true);
//		Node button = new ScriptButton(myScripts, this).getNode();
//		scriptBox.getChildren().add(parameters);
//		scriptBox.getChildren().add(button);
		return addPane(SCRIPTS,scriptBox);

	}

	private TitledPane addPane(String title, Node pane) {
		TitledPane tPane = new TitledPane(title, pane);
		tPane.setAnimated(false);
		return tPane;
	}

	/**Adds a collapse section that displays the map
	 * @param map
	 * @param collapse
	 * @throws GroovyInstantiationException
	 */
	private Node addMap(Map<String,?> map, boolean collapse) throws GroovyInstantiationException {
		CollapsePane pane = new CollapsePane(map, collapse);
		return pane.getNode();
	}
	
	/**Displays a button that allows users to add more scripts to an entity
	 * 
	 */
	private void addButton() {
		CustomButton saveEntity = new CustomButton(new EntitySave(myEntity), "Save Entity");
		myVBox.getChildren().add(saveEntity.getButton());
	}

	public Entity getEntity(){
		return myEntity;
	}

}
