package authoring.panels.tabbable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import util.PropertiesReader;
import util.exceptions.GroovyInstantiationException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ScriptButton {
    private final String ADD = "Add";
    private Map<String, List<String>> myMap;
    private AttributesPanel myPanel;
    private ComboBox comboBox;
    private HBox hbox;

    public ScriptButton(Map<String, List<String>> map, AttributesPanel panel){
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
        myMap.put(type, new ArrayList<String>());
        try {
            myPanel.updateView();
        } catch (GroovyInstantiationException e) {}
    }

    public Node getNode(){
        return hbox;
    }
}
