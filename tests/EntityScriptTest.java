import database.jsonhelpers.JSONDataFolders;
import database.jsonhelpers.JSONDataManager;
import database.jsonhelpers.JSONHelper;
import database.jsonhelpers.JSONToObjectConverter;
import database.scripthelpers.ScriptLoader;
import engine.entities.Entity;
import engine.events.TickEvent;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import javafx.application.Application;
import javafx.stage.Stage;
import org.json.JSONObject;
import sun.font.Script;

public class EntityScriptTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        JSONDataManager manager = new JSONDataManager(JSONDataFolders.ENTITY_BLUEPRINT);
        JSONObject blueprint = manager.readJSONFile("test");
        JSONToObjectConverter<Entity> converter = new JSONToObjectConverter<>(Entity.class);
        Entity entityFromFile = converter.createObjectFromJSON(Entity.class,blueprint);

        String script = ScriptLoader.stringForFile("example.groovy");
        entityFromFile.on("tick", e -> {
            Binding binding = new Binding();
            binding.setVariable("entity", entityFromFile);
            binding.setVariable("game", null);
            new GroovyShell(binding).evaluate(script);
        });

        Entity child = new Entity(entityFromFile);
        new TickEvent().fire(entityFromFile);
        new TickEvent().fire(entityFromFile);
    }
     public static void main(String[] args) {
        launch(args);
    }
}
