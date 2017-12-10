package authoring.panels.attributes;

import authoring.panels.attributes.EventButton;
import authoring.panels.attributes.ScriptProperties;
import authoring.panels.attributes.TPane;
import authoring.panels.tabbable.PropertiesPanel;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.VBox;
import util.exceptions.GroovyInstantiationException;

import java.util.Map;
import java.util.Properties;

public class EventProperties {
    private final Map<String, Map<String, Map<String, Object>>> myMap;
    private VBox eventBox;
    private PropertiesPanel myPanel;

    public EventProperties(Map<String,Map<String,Map<String,Object>>> map, PropertiesPanel panel) throws GroovyInstantiationException {
        eventBox = new VBox();
        myMap = map;
        myPanel = panel;
        for (String name : map.keySet()) {
            eventBox.getChildren().add(
                    makePane(name));
        }

        eventBox.getChildren().add(new EventButton(map,panel).getNode());
    }

    private Node makePane(String name) throws GroovyInstantiationException {
        Node node =  TPane.addChildPane(name, makeScripts(myMap.get(name)));
        node.setOnContextMenuRequested(e->context(e,node,name));
        return node;
    }

    private Node makeScripts(Map<String, Map<String, Object>> map) throws GroovyInstantiationException {
        ScriptProperties script = new ScriptProperties(map, myPanel);
        return script.getNode();
    }

    private void context(ContextMenuEvent event, Node node, String name) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem item1 = new MenuItem("Delete Event");
        item1.setOnAction(e -> {
            remove(name);
        });
        contextMenu.getItems().add(item1);
        contextMenu.show(node,event.getScreenX(), event.getScreenY());
    }

    private void remove(String name){
        myMap.remove(name);
        try {
            myPanel.updateProperties();
        } catch (GroovyInstantiationException e) { }
    }

    public Node getNode(){
        return eventBox;
    }
}
