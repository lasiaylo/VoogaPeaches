import authoring.fsm.FSMGraph;
import authoring.fsm.StateRender;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class FSMGraphTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        StateRender state = new StateRender(50, 50, "hell yeah");

        FSMGraph graph = new FSMGraph();
        graph.addState(state);
        Scene s = new Scene(graph.getRender());
        primaryStage.setScene(s);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
