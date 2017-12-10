package authoring.panels.attributes;

import authoring.panels.tabbable.PropertiesPanel;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import util.exceptions.GroovyInstantiationException;

import java.io.File;
import java.util.Map;

public class ScriptProperties {

    private static final String DELETE_SCRIPT = "Delete Script";
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
                    addScript(name)
            );
        }
        myVBox.getChildren().add(new ScriptButton(map, panel).getNode());
    }

    private Node addScript(String name) throws GroovyInstantiationException {
        Node node = TPane.addChildPane(name, makeParameters(myMap.get(name), myPanel));
        node.setOnContextMenuRequested(e->context(e, node, name));
        return node;
    }

    private void context(ContextMenuEvent event, Node node, String name) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem item1 = new MenuItem(DELETE_SCRIPT);
        item1.setOnAction(e -> {
            remove(name);
        });
        contextMenu.getItems().add(item1);
        contextMenu.show(node,event.getScreenX(), event.getScreenY());
    }

    private Node makeParameters(Map<String, Object> map, PropertiesPanel panel) throws GroovyInstantiationException {
        ParameterProperties parameters = new ParameterProperties(map, panel);
        return parameters.getNode();
    }

    private void remove(String name){
        myMap.remove(name);
        myPanel.update();
    }

    private void handle(DragEvent event) {
        Dragboard db = event.getDragboard();
        if (db.hasFiles())
            event.acceptTransferModes(TransferMode.COPY);
        else
            event.consume();
    }

    private void setControl(DragEvent event) {
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