package fsm;

import authoring.fsm.FSMGraph;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Singular instance of an FSM Visualizer to test if the visuals work
 *
 * @author Simran
 */
public class FSMGraphTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FSMGraph graph = new FSMGraph();
        Scene s = new Scene(graph.getRender());
        s.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> graph.onSceneClick(e));
        primaryStage.setScene(s);
        primaryStage.show();
        primaryStage.setOnHidden(e -> graph.export());
    }

    public static void main(String[] args) {
        launch(args);
    }

}
