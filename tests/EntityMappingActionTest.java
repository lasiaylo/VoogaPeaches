import database.jsonhelpers.JSONDataFolders;
import database.jsonhelpers.JSONDataManager;
import database.jsonhelpers.JSONToObjectConverter;
import engine.entities.Entity;
import engine.events.KeyPressEvent;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.json.JSONObject;

public class EntityMappingActionTest extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        JSONDataManager manager = new JSONDataManager(JSONDataFolders.ENTITY_BLUEPRINT);
        JSONObject blueprint = manager.readJSONFile("test");
        JSONToObjectConverter<Entity> converter = new JSONToObjectConverter<>(Entity.class);
        Entity entityFromFile = converter.createObjectFromJSON(Entity.class,blueprint);

        Group group = new Group();
        Scene s = new Scene(group);
        s.setOnKeyPressed(e -> {
            new KeyPressEvent(e).fire(entityFromFile);
        });

        primaryStage.setScene(s);
        primaryStage.show();
    }
}
