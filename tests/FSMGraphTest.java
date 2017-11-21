import authoring.fsm.FSMGraph;
import authoring.fsm.StateRender;
import engine.fsm.State;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class FSMGraphTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        StateRender state = new StateRender(50, 50, "hell yeah", new State());
        StateRender state2 = new StateRender(400, 400, "hell no", new State());

        FSMGraph graph = new FSMGraph();
        graph.addState(state);
        graph.addState(state2);
        Scene s = new Scene(graph.getRender());
        primaryStage.setScene(s);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
