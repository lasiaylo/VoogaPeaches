package authoring.panels.tabbable;

import java.util.List;
import java.util.Map;
import authoring.Panel;
import authoring.panels.attributes.CollapsePane;
import authoring.panels.attributes.ParameterButton;
import engine.entities.Entity;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
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
	private Entity myEntity;
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
		myEntity = entity;
		myVBox = new VBox();
		myParameters = entity.getProperties();
		myScripts = (Map<String, List<String>>) entity.getProperty("scripts");
		myParameters.remove("scripts");
		updateView();
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
		addPane(parameterBox);
	}

	private void makeScripts() throws GroovyInstantiationException {
		VBox scriptBox = new VBox();
		Node parameters = addMap(myScripts, true);
		addPane(parameters);
	}

	private void addPane(Node pane) {
		TitledPane tPane = new TitledPane(PARAMETERS, pane);
		tPane.setAnimated(false);
		myVBox.getChildren().add(tPane);
	}

	/**Adds a collapse section that displays the map
	 * @param map
	 * @param collapse
	 * @throws GroovyInstantiationException
	 */
	private Node addMap(Map<String,?> map, boolean collapse) throws GroovyInstantiationException {
		System.out.println(map.toString());
		CollapsePane pane = new CollapsePane(map, collapse);
		return pane.getNode();
	}
	
	/**Displays a button that allows users to add more scripts to an entity
	 * 
	 */
	private void addButton() {
		// TODO Auto-generated method stub
	}

}
