package authoring.panel;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 * camera panel inside authoring environment that displays the game
 * @author estellehe
 *
 */
public class CameraPanel extends GridPane{
	
	public CameraPanel(int rowN, int width, int height) {
		this.setPrefWidth(width);
		this.setPrefHeight(height);
		
		int side = height/rowN;
		for (int n = 0; n < rowN; n++) {
			this.getRowConstraints().add(new RowConstraints(side));
		}
		int colN = width/side;
		for (int n = 0; n < colN; n++) {
			this.getColumnConstraints().add(new ColumnConstraints(side));
		}
		this.setGridLinesVisible(true);
		
	}

}
