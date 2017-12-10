package authoring.panels.tabbable;

import authoring.panels.attributes.EventButton;
import authoring.panels.attributes.ScriptProperties;
import authoring.panels.attributes.TPane;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import util.exceptions.GroovyInstantiationException;

import java.util.Map;
import java.util.Properties;

public class EventProperties {
    private VBox eventBox;
    private PropertiesPanel myPanel;

    public EventProperties(Map<String,Map<String,Map<String,Object>>> map, PropertiesPanel panel) throws GroovyInstantiationException {
        eventBox = new VBox();

        for (String name : map.keySet()) {
            eventBox.getChildren().add(
                    TPane.addChildPane(name, makeScripts(map.get(name))));
        }
        eventBox.getChildren().add(new EventButton(map,panel).getNode());
    }

    private Node makeScripts(Map<String, Map<String, Object>> map) throws GroovyInstantiationException {
        ScriptProperties script = new ScriptProperties(map, myPanel);
        return script.getNode();
    }

    public Node getNode(){
        return eventBox;
    }
}
