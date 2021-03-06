package extensions.example;


import extensions.ExtensionWebView;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;

public class SocialMediaApp extends Application {

    private static final double SCREEN_SIZE = 600;

    private Group myRoot;
    private Scene myScene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        myRoot = new Group();
        myScene = new Scene(myRoot, SCREEN_SIZE, SCREEN_SIZE);
        primaryStage.setScene(myScene);
        primaryStage.setResizable(false);

        addButton(primaryStage);

        primaryStage.show();
    }

    private void addButton(Stage s) {
        Button btn = new Button() {
            @Override
            public void fire() {
                Dialog d = new Dialog();
                ExtensionWebView e = new ExtensionWebView("SocialMedia.html",400,400);
                d.getDialogPane().setContent(e.getView());
                d.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                Node closeButton = d.getDialogPane().lookupButton(ButtonType.CLOSE);
                closeButton.managedProperty().bind(closeButton.visibleProperty());
                closeButton.setVisible(false);
                d.showAndWait();
            }
        };
        btn.setPrefHeight(100);
        btn.setPrefWidth(300);
        btn.setText("Launch Extension");
        myRoot.getChildren().add(btn);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
