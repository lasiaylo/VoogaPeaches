import engine.entities.Entity;
import engine.events.Event;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class EventTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Entity entity = new Entity();
        PrintEvent pEvent = new PrintEvent("no");
        entity.on("print", (event -> {
            PrintEvent printEvent = (PrintEvent) event;
            printEvent.printHellYeah();
        }));

        entity.dispatchEvent(pEvent);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
