import database.firebase.TrackableObject;
import database.jsonhelpers.JSONDataFolders;
import database.jsonhelpers.JSONDataManager;
import database.jsonhelpers.JSONToObjectConverter;
import engine.entities.Entity;
import engine.events.TickEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONObject;

public class EntityScriptTest extends Application {
    private TickEvent tickEvent = new TickEvent(1.  / 60);

    @Override
    public void start(Stage primaryStage) throws Exception {

        JSONDataManager manager = new JSONDataManager(JSONDataFolders.ENTITY_BLUEPRINT);
        JSONObject blueprint = manager.readJSONFile("test");
        JSONToObjectConverter<Entity> converter = new JSONToObjectConverter<>(Entity.class);
        Entity entityFromFile = converter.createObjectFromJSON(Entity.class,blueprint);

        //Entity e = (Entity) TrackableObject.objectForUID("2ca91312fdb14d03865fa4e59d8ee69a");
        //System.out.println(e.getProperty("hell yeah"));


        Scene s = new Scene(entityFromFile.getNodes());
        primaryStage.setScene(s);
        primaryStage.show();



    }
    
    public static void main(String[] args) {
    		System.out.println("tets");
    		launch(args);
    		
    }
    
}
