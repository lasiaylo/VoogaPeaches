package authoring.buttons.strategies;

import database.ObjectFactory;
import database.jsonhelpers.JSONDataFolders;
import database.jsonhelpers.JSONDataManager;
import engine.entities.Entity;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONObject;
import util.PropertiesReader;

import java.io.File;

public class EntitySave implements IButtonStrategy{

    public static final String PROMPT = "Filename of Entity";

    private Entity entity;
    private JSONDataManager jsonDataManager;
    private FileChooser fileChooser;
    private Stage s;


    public EntitySave(Entity entity) {
        this.entity = entity;
        jsonDataManager = new JSONDataManager(JSONDataFolders.ENTITY_BLUEPRINT);
        initializeFileChooser();
        s = new Stage();
    }

    public void initializeFileChooser(){
        fileChooser = new FileChooser();
        fileChooser.setTitle(PROMPT);
        fileChooser.setInitialDirectory(new File(PropertiesReader.path("blueprints")));
        FileChooser.ExtensionFilter jsonFiles = new FileChooser.ExtensionFilter("JSON files", "*.json");
        fileChooser.getExtensionFilters().add(jsonFiles);
    }

    public void updateEntity(Entity entity){
        this.entity = entity;
    }

    @Override
    public void fire() {
        File selectedFile = fileChooser.showSaveDialog(s);
        JSONObject jsonObject = new JSONObject(entity.getProperties());
        jsonDataManager.writeJSONFile(selectedFile.getName(), jsonObject);
    }
}