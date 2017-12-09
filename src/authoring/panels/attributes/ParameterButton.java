package authoring.panels.attributes;

import authoring.panels.tabbable.PropertiesPanel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.Map;

public class ParameterButton {
    private final String STRING = "String";
    private final String DOUBLE = "Double";
    private final String BOOLEAN = "Boolean";
    private final String ADD = "Add";
    private PropertiesPanel myPanel;
    private Map<String, Object> myMap;
    private HBox hbox;
    private TextField text;
    private ComboBox comboBox;
    private Button button;

    public ParameterButton(Map<String, Object> map, PropertiesPanel panel) {
        myMap = map;
        hbox = new HBox();
        myPanel = panel;
        makeVisual();
        addtoBox();
    }

    private void addtoBox() {
        hbox.getChildren().add(text);
        hbox.getChildren().add(comboBox);
        hbox.getChildren().add(button);
    }

    public void makeVisual() {
        text = new TextField();
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "String",
                        "Double",
                        "Boolean"
                );
        comboBox = new ComboBox(options);
        button = makeButton();
    }

    private Button makeButton() {
        Button button = new Button(ADD);
        button.setOnAction(e -> add());
        return button;
    }

    private void add() {
        try {
            String string = text.getText();
            String type = comboBox.getSelectionModel().getSelectedItem().toString();
            Object obj = determineType(type);
            myMap.put(string, obj);
            myPanel.updateProperties();
        }catch(Exception e){}
    }

    private Object determineType(String type){
        if (type == STRING){
            String string = "";
            return string;
        }
        if (type == DOUBLE){
            return Double.parseDouble("0");
        }
        if (type == BOOLEAN){
            return Boolean.parseBoolean("false");
        }
        return null;
    }

    public Node getNode(){
        return hbox;
    }
}