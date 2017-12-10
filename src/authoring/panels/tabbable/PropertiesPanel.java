package authoring.panels.tabbable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import authoring.Panel;
import authoring.buttons.CustomButton;
import authoring.buttons.strategies.EntitySave;
import authoring.panels.attributes.*;
import engine.entities.Entity;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.apache.commons.io.FilenameUtils;
import util.ErrorDisplay;
import util.exceptions.GroovyInstantiationException;
import util.pubsub.PubSub;
import util.pubsub.messages.EntityPass;

/**
 * Displays the attributes associated with a particular Entity
 *
 * @author lasia
 */
public class PropertiesPanel implements Panel {
//    Should probably move these strings out to a properties file
    private final String TITLE = "Properties";
    private final String SCRIPTS = "Scripts";
    private final String PARAMETERS = "Parameters";
    private final String EVENTS = "Events";
    private Entity myEntity;
    private VBox myVBox;
    private Map<String, Object> myParameters;
    private Map<String, Map<String, Object>> myScripts;
    private Map<String, Map<String, Map<String, Object>>> myEvents;

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

    /**
     * Updates the view of the AttributesPanel
     *
     * @throws GroovyInstantiationException
     */
    public void updateProperties() throws GroovyInstantiationException {
        updateProperties(myEntity);
    }

    /**
     * Takes in an entity and displays its properties
     *
     * @param entity
     * @throws GroovyInstantiationException
     */
    public void updateProperties(Entity entity) throws GroovyInstantiationException {
        myEntity = entity;
        entity.executeScripts();
        myParameters = myEntity.getProperties();
        myScripts = (Map<String, Map<String, Object>>) myParameters.remove("scripts");
        myEvents = (Map<String, Map<String, Map<String, Object>>>) myParameters.remove("listeners");
        updateVisuals();
        myParameters.put("scripts", myScripts);
        myParameters.put("listeners", myEvents);
    }

    private void updateVisuals() throws GroovyInstantiationException {
        myVBox.getChildren().clear();
        myVBox.getChildren().addAll(
                TPane.addPane(PARAMETERS, makeParameters(myParameters)),
                TPane.addPane(SCRIPTS, makeScripts(myScripts)),
                TPane.addPane(EVENTS,makeEvents(myEvents)),
                addButton()
        );
    }

    private Node makeParameters(Map<String, Object> parameterMap) throws GroovyInstantiationException {
        ParameterProperties parameters = new ParameterProperties(parameterMap, this);
        return parameters.getNode();
    }

    private Node makeScripts(Map<String, Map<String, Object>> map) throws GroovyInstantiationException {
        ScriptProperties scripts = new ScriptProperties(map,this);
        return scripts.getNode();
    }

    private Node makeEvents(Map<String,Map<String,Map<String,Object>>> map) throws GroovyInstantiationException {
        EventProperties events = new EventProperties(map, this);
        return events.getNode();
    }

    /**
     * Displays a button that allows users to save their entity as a blueprint
     */
    private Node addButton() {
        return new CustomButton(new EntitySave(myEntity), "Save Entity").getButton();
    }

    public void addFile(Map<String, Map<String,Object>> map, File file) throws GroovyInstantiationException {
        if (file != null) {
            String fileName = FilenameUtils.removeExtension(file.getName());
            map.put(fileName, new HashMap<>());
        }
        updateProperties();
    }

    public Entity getEntity() {
        return myEntity;
    }

}