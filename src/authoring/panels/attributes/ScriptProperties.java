package authoring.panels.attributes;

import authoring.panels.tabbable.PropertiesPanel;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import org.apache.commons.io.FilenameUtils;
import util.exceptions.GroovyInstantiationException;

import java.io.File;
import java.util.Map;

public class ScriptProperties {
    private final PropertiesPanel myPanel;
    private final Map<String, Map<String, Object>> myMap;
    private VBox myVBox;

    public ScriptProperties(Map<String, Map<String, Object>> map, PropertiesPanel panel) throws GroovyInstantiationException {
        myVBox = new VBox();
        myMap = map;
        myPanel = panel;
        myVBox.setOnDragOver(e->handle(e));
        myVBox.setOnDragDropped(e->setControl(e));
        for (String name : map.keySet()) {
            myVBox.getChildren().add(
                    TPane.addChildPane(name, makeParameters(map.get(name), panel))
            );
        }
        myVBox.getChildren().add(new ScriptButton(map, panel).getNode());
    }

    private Node makeParameters(Map<String, Object> map, PropertiesPanel panel) throws GroovyInstantiationException {
        ParameterProperties parameters = new ParameterProperties(map, panel);
        return parameters.getNode();
    }

    public void handle(DragEvent event) {
        Dragboard db = event.getDragboard();
        if (db.hasFiles())
            event.acceptTransferModes(TransferMode.COPY);
        else
            event.consume();
    }

    public void setControl(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            success = true;
            String filePath = null;
            for (File file:db.getFiles()) {
                addFile(file);
            }
        }
        event.setDropCompleted(success);
        event.consume();
    }

    private void addFile(File file ) {
        try {
            myPanel.addFile(myMap, file);
        } catch (GroovyInstantiationException e) { }
    }

    public Node getNode(){
        return myVBox;
    }
}
