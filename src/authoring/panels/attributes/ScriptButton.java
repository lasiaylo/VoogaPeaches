package authoring.panels.attributes;

import authoring.panels.tabbable.PropertiesPanel;
import database.jsonhelpers.JSONDataManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import org.apache.commons.io.FilenameUtils;
import util.PropertiesReader;
import util.exceptions.GroovyInstantiationException;

import java.io.File;
import java.util.*;

public class ScriptButton {

    private static final String GROOVY_FILES_PROMPT = "Groovy Files";
    private static final String GROOVY_EXTENSION = "*.groovy";
    private static final String FILEPATHS = "filepaths";
    private static final String DB_SCRIPTS = "db_scripts";
    private final String ADD = "Add Script";
    private Map<String, Map<String, Object>> myMap;
    private PropertiesPanel myPanel;
    private Button myButton;
    private FileChooser fileChooser;

    public ScriptButton(Map<String, Map<String, Object>> map, PropertiesPanel panel){
        myMap = map;
        myPanel = panel;
        makeVisual();
    }

    private void makeVisual() {
        createFileChooser();
        myButton = makeButton();
    }

    private void createFileChooser() {
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter groovy = new FileChooser.ExtensionFilter(GROOVY_FILES_PROMPT, GROOVY_EXTENSION);
        fileChooser.getExtensionFilters().add(groovy);
        fileChooser.setInitialDirectory(new File(PropertiesReader.value(FILEPATHS, DB_SCRIPTS)));
    }

    private Button makeButton() {
        Button button = new Button(ADD);
        button.setOnAction(e-> add());
        return button;
    }

    private void add() {
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                myPanel.addFile(myMap, file);
            } catch (GroovyInstantiationException e) { }
        }
    }

    public Node getNode(){
        return myButton;
    }
}