package fsm;

import authoring.fsm.FSMPanelLocal;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FSMPanelTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FSMPanelLocal graph = new FSMPanelLocal();
        Scene s = new Scene(graph.getRegion());
        primaryStage.setScene(s);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
