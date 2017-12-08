package authoring.panels.attributes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.Map;


public class ParameterButton {
    private final String CLASS = "java.lang.";
    private Map<String, Object> myMap;
    private HBox hbox;
    private TextField text;
    private ComboBox comboBox;

    public ParameterButton(Map<String, Object> map) {
        myMap = map;
        makeVisual();
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
        Button button = makeButton();
    }

    private Button makeButton() {
        Button button = new Button("Add");
        button.setOnAction(e -> add());
        return button;
    }

    private void add() {
        try {
            String string = text.getText();
            String type = comboBox.getSelectionModel().getSelectedItem().toString();
            Class clazz = Class.forName(CLASS + type);
            myMap.put(string, clazz.newInstance());
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Node getNode(){
        return hbox;
    }
}
