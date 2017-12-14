package authoring.panels.attributes;

import authoring.panels.tabbable.PropertiesPanel;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.HashMap;
import java.util.Map;

public class EventButton {
    private static final String EVENTS = "events";
    private final String ADD = "Add Event";
    private Map<String, Map<String, Map<String, Object>>> myMap;
    private PropertiesPanel myPanel;
    private HBox hbox;
    private TextField textfield;

    public EventButton(Map<String, Map<String, Map<String, Object>>> map, PropertiesPanel panel){
        myMap = map;
        myPanel = panel;
        hbox = new HBox();
        makeVisual();
    }

    private void makeVisual() {
        textfield = new TextField();
        Button button = makeButton();
        hbox.getChildren().add(textfield);
        hbox.getChildren().add(button);
    }

    private Button makeButton() {
        Button button = new Button(ADD);
        button.setOnAction(e-> add());
        return button;
    }

    private void add() {
        try {
            String type = textfield.getText();
            myMap.put(type, createMap());
            myPanel.update();
        } catch (Exception e) {}
    }

    private Map<String, Map<String, Object>> createMap() {
        Map<String, Map<String, Object>> newMap = new HashMap<String, Map<String, Object>>();
        return newMap;
    }

    public Node getNode(){
        return hbox;
    }
}