package database.examples.filestorage;

import database.FileStorageConnector;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.util.Duration;

public class TestApp extends Application {

    private Group myRoot;
    private ImageView myView;
    private ListView<String> table;
    private FileStorageConnector connector;

    /*
     * Test application that is meant to show off how to load images
     * to and from the FileStorage online
     */

    /* Main Methods used for communication with FileStorageConnector */
    private void loadImage(String imageName) {
        Image img = connector.retrieveImage(imageName);
        myView.setImage(img);
    }

    private void saveImage(File img) {
        try {
            connector.saveFile(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /* JavaFX Methods Used for GUI. Not Important */

    @Override
    public void start(Stage primaryStage) throws Exception {
        connector = new FileStorageConnector();

        myRoot = new Group();
        Scene myScene = new Scene(myRoot, 800, 800);
        myScene.setFill(Color.LIGHTGRAY);
        primaryStage.setScene(myScene);
        primaryStage.setResizable(false);

        setupToolbar(primaryStage);
        setupImageView();
        setupTableView();

        KeyFrame frame = new KeyFrame(Duration.millis(300), e -> step());
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();

        primaryStage.show();

    }

    private void step() {
        table.setItems(FXCollections.observableArrayList(connector.fileNames()));
    }

    private void setupImageView() {
        File f = new File("resources/graphics/sprite_test.png");
        try {
            Image img = new Image(new FileInputStream(f));
            myView = new ImageView(img);
        } catch(FileNotFoundException e) {
            myView = new ImageView();
        }
        myView.setY(40);
        myView.setX(100);
        myView.setFitHeight(770);
        myView.setFitWidth(700);
        myView.prefWidth(600);
        myView.prefHeight(770);
        myRoot.getChildren().add(myView);
    }

    private void setupTableView() {
        table = new ListView<>();
        table.setLayoutY(40);
        table.setPrefWidth(200);
        table.setPrefHeight(770);
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loadImage(table.getSelectionModel().getSelectedItem());
            }
        });
        myRoot.getChildren().add(table);
    }

    private void setupToolbar(Stage s) {
        Button fileChooseBtn = new Button() {
          @Override
          public void fire() {
              FileChooser fc = new FileChooser();
              File img = fc.showOpenDialog(s);
              if(img != null) saveImage(img);
          }
        };
        fileChooseBtn.setText("Save Image Online");
        ToolBar topBar = new ToolBar(fileChooseBtn);
        topBar.setPrefWidth(800);
        topBar.setPrefHeight(30);
        myRoot.getChildren().add(topBar);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
