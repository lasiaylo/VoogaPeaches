package authoring.panels.attributes;

import authoring.panels.tabbable.PropertiesPanel;
import engine.entities.Entity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import util.PropertiesReader;
import util.exceptions.GroovyInstantiationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ScriptButton {
    private final String ADD = "Add";
    private Map<String, List<String>> myMap;
    private PropertiesPanel myPanel;
    private ComboBox comboBox;
    private HBox hbox;
    private Entity entity;

    public ScriptButton(Map<String, List<String>> map, PropertiesPanel panel, Entity entity){
        myMap = map;
        myPanel = panel;
        hbox = new HBox();
        makeVisual();
        this.entity = entity;
    }

    private void makeVisual() {
        Set<String> events= PropertiesReader.map("events").keySet();
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
        myMap.put(type, new ArrayList<String>());
        try {
            myPanel.updateView();
            entity.initialize();
        } catch (GroovyInstantiationException e) {}
    }

    public Node getNode(){
        return hbox;
    }
}