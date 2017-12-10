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

public class EntitySave implements IButtonStrategy{

    private static final String PROMPT = "Filename of Entity";
    private static final String DEFAULT_NAME = "UserEntity";
    private static final String BLUEPRINTS_FILEPATH = "blueprints";
    private static final String JSON_EXTENSION_FILTER = "JSON files";
    private static final String JSON_FILE = "*.json";

    private Entity entity;
    private FileChooser fileChooser;
    private JSONDataManager jsonDataManager;
    private Stage s;

    public EntitySave(Entity entity) {
        this.entity = entity;
        initializeFileChooser();
        jsonDataManager = new JSONDataManager(JSONDataFolders.USER_DEFINED_ENTITY);
        s = new Stage();
    }

    public void initializeFileChooser(){
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
            jsonDataManager.writeJSONFile(selectedFile.getName(), jsonObject);
        }
    }
}