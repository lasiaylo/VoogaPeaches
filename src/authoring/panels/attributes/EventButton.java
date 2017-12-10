package authoring.panels.attributes;

import authoring.panels.tabbable.PropertiesPanel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import util.PropertiesReader;
import util.exceptions.GroovyInstantiationException;

import java.util.*;

public class EventButton {
    private final String ADD = "Add Event";
    private Map<String, Map<String, Map<String, Object>>> myMap;
    private PropertiesPanel myPanel;
    private ComboBox comboBox;
    private HBox hbox;

    public EventButton(Map<String, Map<String, Map<String, Object>>> map, PropertiesPanel panel){
        myMap = map;
        myPanel = panel;
        hbox = new HBox();
        makeVisual();
    }

    private void makeVisual() {
        Collection<String> events= PropertiesReader.map("events").keySet();
        ObservableList<String> options = FXCollections.observableArrayList(events);
        comboBox = new ComboBox(options);

        Button button = makeButton();
        hbox.getChildren().add(comboBox);
        hbox.getChildren().add(button);
    }

    private Button makeButton() {
        Button button = new Button(ADD);
        button.setOnAction(e-> add());
        return button;
    }

    private void add() {
        String type = comboBox.getSelectionModel().getSelectedItem().toString();
        myMap.put(type, createMap());
        try {
            myPanel.updateProperties();
        } catch (GroovyInstantiationException e) {}
    }

    private Map<String, Map<String, Object>> createMap() {
        Map<String, Map<String, Object>> newMap = new HashMap<String, Map<String, Object>>();
        return newMap;
    }

    public Node getNode(){
        return hbox;
    }
}