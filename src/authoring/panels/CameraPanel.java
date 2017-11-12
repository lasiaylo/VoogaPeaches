package authoring.panels;

import authoring.Screen;
import authoring.Panel;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;

/**
 * camera panel inside authoring environment that displays the game
 * @author estellehe
 *
 */
public class CameraPanel implements Panel{

	private StackPane camera; //Suggestion: Use a stackpane to add entities directly on top of the grid
	private GridPane background;
	
	public CameraPanel(int rowN, int width, int height) {
	    camera = new StackPane();
	    background = new GridPane();

		background.setPrefWidth(width);
		background.setPrefHeight(height);
		
		int side = height/rowN;
		for (int n = 0; n < rowN; n++) {
			background.getRowConstraints().add(new RowConstraints(side));
		}
		int colN = width/side;
		for (int n = 0; n < colN; n++) {
			background.getColumnConstraints().add(new ColumnConstraints(side));
		}
		background.setGridLinesVisible(true);

		camera.getChildren().addAll(background);
	}

    @Override
    public Region getRegion() {
        return camera;
    }

    @Override
    public int getArea() {
        return Screen.CAMERA;
    }

    @Override
    public void setController() {

    }

    @Override
    public String title(){
	    return "Game Camera";
	}
}
