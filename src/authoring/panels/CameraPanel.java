package authoring.panels;

import java.util.ResourceBundle;

import authoring.Panel;
import authoring.Screen;
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
	
	private GridPane myGridPane;
	private ResourceBundle properties = ResourceBundle.getBundle("screenlayout");
	private double cameraWidth = Double.parseDouble(properties.getString("camerawidth"));
	private double cameraHeight = Double.parseDouble(properties.getString("cameraheight"));
	private int camerarowN = Integer.parseInt(properties.getString("camerarowN"));
	
	public CameraPanel() {
		myGridPane = new GridPane();
		myGridPane.setPrefWidth(cameraWidth);
		myGridPane.setPrefHeight(cameraHeight);
		
		double side = cameraHeight/camerarowN;
		for (int n = 0; n < camerarowN; n++) {
			myGridPane.getRowConstraints().add(new RowConstraints(side));
		}
		double colN = cameraWidth/side;
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
		return Screen.CAMERA;
	}

	@Override
	public void setController() {
		// TODO Auto-generated method stub
		
	}

}
