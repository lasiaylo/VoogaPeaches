import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class TabPaneTester extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = sizeScene();
        primaryStage.setMinHeight(200);
        primaryStage.setWidth(475);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Scene sizeScene(){
        TabPane tabPane = new TabPane();
        tabPane.setStyle("-fx-open-tab-animation: NONE; -fx-close-tab-animation: NONE;");
        tabPane.setTabMinWidth(200);
        tabPane.getTabs().addAll(newTabs(3));
        Scene scene = new Scene(tabPane);
        ObservableList<Tab> tabs = tabPane.getTabs();
        PauseTransition p = new PauseTransition(Duration.millis(150 + 20));
        scene.setOnKeyPressed(e -> {
            Tab remove = tabs.remove(0);
            p.setOnFinished(e2 -> tabs.add(remove));
            p.play();
        });
        return scene;
    }

    private static Tab[] newTabs(int numTabs){
        Tab[] tabs = new Tab[numTabs];
        for(int i = 0; i < numTabs; i++) {
            Label label = new Label("Tab Number " + (i + 1));
            Tab tab = new Tab();
            tab.setGraphic(label);
            tabs[i] = tab;
        }
        return tabs;
    }

    public static void main(String[] args) {
        launch();
    }
}