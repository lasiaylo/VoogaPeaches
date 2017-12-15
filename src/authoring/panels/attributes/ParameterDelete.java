package authoring.panels.attributes;

import javafx.scene.Node;
import javafx.scene.control.Button;

import java.util.Map;

public class ParameterDelete {

    private final Updatable myUpdate;
    private final Button myButton;
    private final Map<String, Object> myMap;
    private final String myKey;

    public ParameterDelete(Map<String, Object> parameterMap, String key, Updatable update) {
        myMap = parameterMap;
        myUpdate = update;
        myKey = key;
        myButton = new Button("X");
        myButton.setOnAction(e->delete());
    }

    private void delete() {
        myMap.remove(myKey);
        myUpdate.update();
    }

    public Node getNode() {
        return myButton;
    }
}
