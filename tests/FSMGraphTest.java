import authoring.fsm.FSMGraph;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class FSMGraphTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FSMGraph graph = new FSMGraph();
        Scene s = new Scene(graph.getRender());
        s.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> graph.onSceneClick(e));
        primaryStage.setScene(s);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
