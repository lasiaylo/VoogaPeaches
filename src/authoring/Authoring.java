package authoring;

import authoring.panel.MainPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * main class for authoring
 * @author estellehe
 *
 */

public class Authoring extends Application {
	private static final int SCENE_WIDTH = 1000;
	private static final int SCENE_HEIGHT = 600;
	


	@Override
	public void start(Stage primaryStage) throws Exception {
		MainPane root = new MainPane(SCENE_WIDTH, SCENE_HEIGHT);
		Scene scene = new Scene(root,SCENE_WIDTH, SCENE_HEIGHT);
		
		primaryStage.setTitle("Voogasalad");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public void startSimulation(String[] args) {
		launch(args);
	}


}
