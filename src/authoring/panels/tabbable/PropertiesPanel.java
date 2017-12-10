package authoring.panels.tabbable;

import java.util.List;
import java.util.Map;
import authoring.Panel;
import authoring.buttons.CustomButton;
import authoring.buttons.strategies.EntitySave;
import authoring.panels.attributes.*;
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
	private final String TITLE = "Properties";
	private final String SCRIPTS = "Scripts";
	private final String PARAMETERS = "Parameters";
	private final String BINDINGS = "bindings";
	private final String ACTIONS = "actions";
	private Entity myEntity;
	private VBox myVBox;
	private Map<String, Object> myParameters;
	private List<Map<String, Object>> myScripts;

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
		getRegion().getStyleClass().add("panel");
	}

	@Override
	public Region getRegion() {
		return myVBox;
	}

	@Override
	public String title() {
		return TITLE;
	}

	/**Updates the view of the AttributesPanel
	 * @throws GroovyInstantiationException
	 */
	public void updateProperties() throws GroovyInstantiationException {
		updateProperties(myEntity);
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
		entity.initialize();
		updateVisuals();
	}

	private void updateVisuals() throws GroovyInstantiationException {
		myVBox.getChildren().clear();
		myParameters = myEntity.getProperties();
		myScripts = (List<Map<String, Object>>) myParameters.remove("scripts");
		myVBox.getChildren().addAll(
				makeParameters(myParameters),
				makeScripts()
		);
		addButton();
		myParameters.put("scripts", myScripts);
	}


	private Node makeParameters(Map<String, Object> parameterMap) throws GroovyInstantiationException {
		VBox parameterBox = new VBox();
		Node parameters = addMap(parameterMap,false);
		Node button = new ParameterButton(parameterMap, this).getNode();
		parameterBox.getChildren().addAll(parameters,button);
		return addPane(PARAMETERS, parameterBox);
	}


	private Node makeScripts() throws GroovyInstantiationException {
		VBox scriptBox = new VBox();

		for (Map<String, Object> map : myScripts){
			String name = (String) map.remove("name");

			scriptBox.getChildren().add(
					addPane(name, makeParameters(map)));
			map.put("name", name);
		}
//		scriptBox.getChildren().add(new ScriptButton(myScripts, this).getNode());
		return addPane(SCRIPTS,scriptBox);
	}

	private void makeEvents(){
		VBox eventBox = new VBox();
//		Map<String, Object> event = myScripts.get(s);
//		Map<String, Object> bindings = (Map<String, Object>) myScripts.get(s).get(BINDINGS);
	}


	private Node makeList(Map<String, Object> event) throws GroovyInstantiationException {
		Field field = FieldFactory.makeFieldMap(event, ACTIONS);
		Node node = field.getControl();
		return addPane(ACTIONS,field.getControl());
	}

	private TitledPane addChildPane(String title, Node...pane) {
		VBox box = new VBox();
		box.getChildren().addAll(pane);
		return addPane(title, box);
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