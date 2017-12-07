import engine.entities.Entity;
import engine.events.Event;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class EventTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Entity entity = new Entity();
        PrintEvent pEvent = new PrintEvent("print");
        entity.on("print", (event -> {
            PrintEvent printEvent = (PrintEvent) event;
            printEvent.printHellYeah();
        }));

        Entity child = new Entity();
        entity.add(child);

        child.on("print", (event -> {
            PrintEvent printEvent = (PrintEvent) event;
            System.out.println("hell no");
        }));

        entity.dispatchEvent(pEvent);
        pEvent.fire(entity);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
