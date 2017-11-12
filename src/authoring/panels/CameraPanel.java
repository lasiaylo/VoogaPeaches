package authoring.panels;

import authoring.Panel;
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
	private static final int CAMERAAREA = 4;
	
	private GridPane myGridPane;
	
	public CameraPanel(int rowN, int width, int height) {
		myGridPane = new GridPane();
		myGridPane.setPrefWidth(width);
		myGridPane.setPrefHeight(height);
		
		int side = height/rowN;
		for (int n = 0; n < rowN; n++) {
			myGridPane.getRowConstraints().add(new RowConstraints(side));
		}
		int colN = width/side;
		for (int n = 0; n < colN; n++) {
			myGridPane.getColumnConstraints().add(new ColumnConstraints(side));
		}
		myGridPane.setGridLinesVisible(true);
		
	}

	@Override
	public Region getRegion() {
		// TODO Auto-generated method stub
		return myGridPane;
	}

	@Override
	public int getArea() {
		return CAMERAAREA;
	}

	@Override
	public void setController() {
		// TODO Auto-generated method stub
		
	}

}
