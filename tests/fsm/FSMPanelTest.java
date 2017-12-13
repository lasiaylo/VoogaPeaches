package fsm;

import authoring.panels.tabbable.FSMPanel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FSMPanelTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FSMPanel graph = new FSMPanel();
        Scene s = new Scene(graph.getRegion());
        primaryStage.setScene(s);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
