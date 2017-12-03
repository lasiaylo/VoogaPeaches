import database.jsonhelpers.JSONDataFolders;
import database.jsonhelpers.JSONDataManager;
import database.jsonhelpers.JSONHelper;
import database.jsonhelpers.JSONToObjectConverter;
import database.scripthelpers.ScriptLoader;
import engine.entities.Entity;
import javafx.application.Application;
import javafx.stage.Stage;
import org.json.JSONObject;

public class EntityScriptTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        JSONDataManager manager = new JSONDataManager(JSONDataFolders.ENTITY_BLUEPRINT);
        JSONObject blueprint = manager.readJSONFile("test");
        JSONToObjectConverter<Entity> converter = new JSONToObjectConverter<>(Entity.class);
        Entity entityFromFile = converter.createObjectFromJSON(Entity.class,blueprint);

    }
     public static void main(String[] args) {
        launch(args);
    }
}
