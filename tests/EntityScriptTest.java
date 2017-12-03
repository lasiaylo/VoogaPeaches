import database.scripthelpers.ScriptLoader;
import engine.entities.Entity;
import javafx.application.Application;
import javafx.stage.Stage;

public class EntityScriptTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Entity root = new Entity();
        ScriptLoader scriptLoader = new ScriptLoader();
    }
     public static void main(String[] args) {
        launch(args);
    }
}
