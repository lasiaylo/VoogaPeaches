import engine.entities.Entity;
import engine.fsm.StateManager;
import javafx.stage.Stage;

public class StateTest extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        Entity entity = new Entity();
        State curr = new State("state 2");
        State def = new State("default state");
        StateManager sm = new StateManager(curr, def);
        entity.on("state 2", (event -> {
            System.out.println("in state2");
        }));       
        entity.dispatchEvent(sm.getCurrentState());
    }

    public static void main(String[] args) {
        launch(args);
    }
}