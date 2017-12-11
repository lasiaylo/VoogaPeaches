package authoring.panels.tabbable;

import authoring.Panel;
import authoring.buttons.CustomButton;
import authoring.buttons.strategies.EntitySave;
import authoring.buttons.strategies.Update;
import authoring.panels.attributes.*;
import engine.entities.Entity;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.apache.commons.io.FilenameUtils;
import util.ErrorDisplay;
import util.exceptions.GroovyInstantiationException;
import util.pubsub.PubSub;
import util.pubsub.messages.EntityPass;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Displays the attributes associated with a particular Entity
 *
 * @author lasia
 * @author Richard Tseng
 */
public class PropertiesPanel implements Panel,Updatable {

    private static final String ENTITY_PASS = "ENTITY_PASS";
    private static final String GROOVY_ERROR = "Groovy Error";
    private static final String GROOVY_ERROR_PROMPT = "You're trying to set something incorrectly, bro! Not Groovy!";
    private static final String PANEL = "panel";
    private static final String LISTENERS = "listeners";
    private static final String SAVE_ENTITY = "Save Entity";
    private static final String UPDATE_ENTITY = "Update Entity";
    private final String TITLE = "Properties";
    private final String SCRIPTS = "scripts";
    private final String PARAMETERS = "Parameters";
    private final String EVENTS = "Events";
    private Entity myEntity;
    private VBox myVBox;
    private Map<String, Object> myParameters;
    private Map<String, Map<String, Object>> myScripts;
    private Map<String, Map<String, Map<String, Object>>> myEvents;

	public PropertiesPanel() {
		myVBox = new VBox();
		PubSub.getInstance().subscribe(ENTITY_PASS, e -> {
			EntityPass ePass = (EntityPass) e;
			try {
				this.updateProperties(ePass.getEntity());
			} catch (GroovyInstantiationException exception) {
				new ErrorDisplay(GROOVY_ERROR,
                        GROOVY_ERROR_PROMPT).displayError();
			}
		});
		getRegion().getStyleClass().add(PANEL);
	}

    @Override
    public Region getRegion() {
        return new ScrollPane(myVBox);
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
    public void update() {
        try {
            updateProperties(myEntity);
        } catch (GroovyInstantiationException e) { }
    }

    /**
     * Takes in an entity and displays its properties
     *
     * @param entity
     * @throws GroovyInstantiationException
     */
    public void updateProperties(Entity entity) throws GroovyInstantiationException {
        myEntity = entity.substitute();
        myParameters = myEntity.getProperties();
        myScripts = (Map<String, Map<String, Object>>) myParameters.remove(SCRIPTS);
        myEvents = (Map<String, Map<String, Map<String, Object>>>) myParameters.remove(LISTENERS);
        updateVisuals();
        myParameters.put(SCRIPTS, myScripts);
        myParameters.put(LISTENERS, myEvents);
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
        HBox hbox = new HBox();
        hbox.getChildren().add(new CustomButton(new EntitySave(myEntity), SAVE_ENTITY).getButton());
        hbox.getChildren().add(new CustomButton(new Update(this), UPDATE_ENTITY).getButton());
        return hbox;
    }

    public void addFile(Map<String, Map<String,Object>> map, File file) throws GroovyInstantiationException {
        if (file != null) {
            String fileName = FilenameUtils.removeExtension(file.getName());
            map.put(fileName, new HashMap<>());
        }
        update();
    }

    public Entity getEntity() {
        return myEntity;
    }
}