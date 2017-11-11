package authoring;

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
	


	@Override
	public void start(Stage primaryStage) throws Exception {
		Screen myScreen = new Screen(primaryStage);
		
	}
	
	public void startSimulation(String[] args) {
		launch(args);
	}


}
