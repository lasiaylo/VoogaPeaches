package authoring.panels.tabbable;

import java.io.File;
import java.util.HashMap;
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
    private final String TITLE = "Properties";
    private final String SCRIPTS = "Scripts";
    private final String PARAMETERS = "Parameters";
    private final String BINDINGS = "bindings";
    private final String ACTIONS = "actions";
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

    public void updateProperties(String UID) throws GroovyInstantiationException {
        Entity entity = (Entity) TrackableObject.objectForUID(UID);
        updateProperties(entity);
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
        updateVisuals();
    }

    private void updateVisuals() throws GroovyInstantiationException {
        myVBox.getChildren().clear();
        myParameters = myEntity.getProperties();
        myScripts = (Map<String, Map<String, Object>>) myParameters.remove("scripts");
        myEvents = (Map<String, Map<String, Map<String, Object>>>) myParameters.remove("listeners");
        myVBox.getChildren().addAll(
                addPane(PARAMETERS, makeParameters(myParameters)),
                addPane(SCRIPTS, makeScripts(myScripts)),
                makeEvents()
        );
        addButton();
        myParameters.put("scripts", myScripts);
        myParameters.put("listeners", myEvents);
    }

    private Node makeParameters(Map<String, Object> parameterMap) throws GroovyInstantiationException {
        ParameterProperties parameters = new ParameterProperties(parameterMap, this);
        return parameters.getNode();
    }

    private Node makeScripts(Map<String, Map<String, Object>> map) throws GroovyInstantiationException {
        ScriptProperties scripts = new ScriptProperties(map,this);
        return scripts.getNode();
    }

    private Node makeEvents() throws GroovyInstantiationException {
        VBox eventBox = new VBox();
        for (String name : myEvents.keySet()) {
            eventBox.getChildren().add(
                    addChildPane(name, makeScripts(myEvents.get(name))));
        }
        return addPane("Events", eventBox);
    }

    private Node makeList(Map<String, Object> event) throws GroovyInstantiationException {
        Field field = FieldFactory.makeFieldMap(event, ACTIONS);
        Node node = field.getControl();
        return addPane(ACTIONS, field.getControl());
    }

    private TitledPane addChildPane(String title, Node... pane) {
        VBox box = new VBox();
        box.getChildren().addAll(pane);
        return addPane(title, box);
    }

    private TitledPane addPane(String title, Node pane) {
        TitledPane tPane = new TitledPane(title, pane);
        tPane.setAnimated(false);
        return tPane;
    }

    /**
     * Adds a collapse section that displays the map
     *
     * @param map
     * @param collapse
     * @throws GroovyInstantiationException
     */
    private Node addMap(Map<String, ?> map, boolean collapse) throws GroovyInstantiationException {
        CollapsePane pane = new CollapsePane(map, collapse);
        return pane.getNode();
    }

    /**
     * Displays a button that allows users to add more scripts to an entity
     */
    private void addButton() {
        CustomButton saveEntity = new CustomButton(new EntitySave(myEntity), "Save Entity");
        myVBox.getChildren().add(saveEntity.getButton());
    }

    public void addFile(Map<String, Map<String,Object>> map, File file) throws GroovyInstantiationException {
        if (file != null) {
            String fileName = FilenameUtils.removeExtension(file.getName());
            map.put(fileName, new HashMap<String, Object>());
        }
        updateProperties();
    }

    public Entity getEntity() {
        return myEntity;
    }

}