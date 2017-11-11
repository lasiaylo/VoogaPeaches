package authoring.panel;

import javafx.scene.layout.BorderPane;


/**
 * main pane that holds all the panels 
 * @author estellehe
 *
 */
public class MainPane extends BorderPane{
	private static final int ROWNUM = 20;
	
	private CameraPanel myCameraPanel;
	
	public MainPane(int width, int height) {
		myCameraPanel = new CameraPanel(ROWNUM, width, height);
		this.getChildren().add(myCameraPanel);
		this.setPrefWidth(width);
		this.setPrefHeight(height);
	}

}
