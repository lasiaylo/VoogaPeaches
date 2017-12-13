package authoring.buttons.strategies;

import database.jsonhelpers.JSONDataFolders;
import database.jsonhelpers.JSONDataManager;
import database.jsonhelpers.JSONHelper;
import engine.entities.Entity;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONObject;
import util.PropertiesReader;
import java.io.File;

/**
 * The IButtonStrategy that is used in order to allow for the creation
 * of the save button within the authoring environment that is then used
 * to allow users to save custom made Entities
 *
 * @author Richard Tseng
 */
public class EntitySave implements IButtonStrategy{

    private static final String PROMPT = "Filename of Entity";
    private static final String DEFAULT_NAME = "UserEntity";
    private static final String BLUEPRINTS_FILEPATH = "blueprints";
    private static final String JSON_EXTENSION_FILTER = "JSON files";
    private static final String JSON_FILE = "*.json";

    /* Instance Variables */
    private Entity entity;
    private FileChooser fileChooser;
    private JSONDataManager jsonDataManager;
    private Stage s;

    public EntitySave(Entity entity) {
        this.entity = entity;
        initializeFileChooser();
        jsonDataManager = new JSONDataManager(JSONDataFolders.ENTITY_BLUEPRINT);
        s = new Stage();
    }

    /**
     * Sets up the initial file chooser that allows the user pick where they want
     * to save their custom entity blueprint
     */
    private void initializeFileChooser(){
        fileChooser = new FileChooser();
        fileChooser.setTitle(PROMPT);
        fileChooser.setInitialFileName(DEFAULT_NAME);
        fileChooser.setInitialDirectory(new File(PropertiesReader.path(BLUEPRINTS_FILEPATH)));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(JSON_EXTENSION_FILTER, JSON_FILE));
    }

    @Override
    public void fire() {
        File selectedFile = fileChooser.showSaveDialog(s);
        if (selectedFile != null){
            JSONObject jsonObject =  JSONHelper.JSONForObject(entity);
            jsonObject.remove("UID");
            jsonDataManager.writeJSONFile(selectedFile.getName(), jsonObject);
        }
    }
}