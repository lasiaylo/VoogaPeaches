package authoring.panels.attributes;

import authoring.panels.tabbable.PropertiesPanel;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import util.exceptions.GroovyInstantiationException;

import java.util.Map;

public class ParameterProperties {
    private VBox parameterBox = new VBox();

    public ParameterProperties(Map<String, Object> parameterMap, UpdatablePanel panel) throws GroovyInstantiationException {
        Node parameters = new CollapsePane(parameterMap,false).getNode();
        Node button = new ParameterButton(parameterMap, panel).getNode();
        parameterBox.getChildren().addAll(parameters,button);
    }

    public Node getNode(){
        return parameterBox;
    }
}
