import authoring.fsm.FSMGraph;
import database.jsonhelpers.JSONDataFolders;
import database.jsonhelpers.JSONDataManager;
import database.jsonhelpers.JSONToObjectConverter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ReadFSMGraphTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        JSONDataManager j = new JSONDataManager(JSONDataFolders.FSM);
        JSONToObjectConverter<FSMGraph> m = new JSONToObjectConverter<>(FSMGraph.class);
        FSMGraph graph = m.createObjectFromJSON(FSMGraph.class, j.readJSONFile("testFSM.json"));
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
