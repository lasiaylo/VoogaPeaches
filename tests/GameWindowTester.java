import authoring.GameWindow.GameWindow;
import database.ObjectFactory;
import engine.entities.Entity;
import javafx.application.Application;
import javafx.stage.Stage;
import util.exceptions.ObjectBlueprintNotFoundException;

public class GameWindowTester extends Application {
    public static void main(String[] args) {
        launch();
    }


    private static Entity createEntity() throws ObjectBlueprintNotFoundException {
        ObjectFactory factory = new ObjectFactory("mario");
        return factory.newObject();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Entity entity = null;
        try {
            entity = createEntity();
        } catch (ObjectBlueprintNotFoundException e) {
            e.printStackTrace();
        }
        GameWindow game = new GameWindow(entity);
    }
}
