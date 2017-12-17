package authoring.panels.attributes;

import authoring.panels.tabbable.PropertiesPanel;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.VBox;
import util.exceptions.GroovyInstantiationException;

import java.util.Map;

public class EventProperties {

    private static final String DELETE_EVENT = "Delete Event";
    private final Map<String, Map<String, Map<String, Object>>> myMap;
    private VBox eventBox;
    private PropertiesPanel myPanel;

    public EventProperties(Map<String,Map<String,Map<String,Object>>> map, PropertiesPanel panel) throws GroovyInstantiationException {
        eventBox = new VBox();
        myMap = map;
        myPanel = panel;
        for (String name : map.keySet()) {
            eventBox.getChildren().add(makePane(name));
        }

        eventBox.getChildren().add(new EventButton(map,panel).getNode());
    }

    private Node makePane(String name) throws GroovyInstantiationException {
        Label label = new Label(name);
        label.setOnContextMenuRequested(e->context(e,label,name));
        TitledPane node =  (TitledPane) TPane.addChildPane(label, makeScripts(myMap.get(name)));
        return node;
    }

    private Node makeScripts(Map<String, Map<String, Object>> map) throws GroovyInstantiationException {
        ScriptProperties script = new ScriptProperties(map, myPanel);
        return script.getNode();
    }

    private void context(ContextMenuEvent event, Node node, String name) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem item1 = new MenuItem(DELETE_EVENT);
        item1.setOnAction(e -> {
            remove(name);
        });
        contextMenu.getItems().add(item1);
        contextMenu.show(node,event.getScreenX(), event.getScreenY());
    }

    private void remove(String name) {
        myMap.remove(name);
        myPanel.update();
    }

    public Node getNode(){
        return eventBox;
    }
}