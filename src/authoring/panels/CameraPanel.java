package authoring.panels;

import java.util.ResourceBundle;

import authoring.Panel;
import authoring.PanelController;
import authoring.Screen;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

/**
 * camera panel inside authoring environment that displays the game
 * @author estellehe
 *
 */
public class CameraPanel implements Panel{
	private static final String PLAY = "Play";
	private static final String PAUSE = "Pause";
	
	private GridPane myGridPane;
	private Button myPlay;
	private Button myPause;
	private VBox myArea;
	private ResourceBundle properties = ResourceBundle.getBundle("screenlayout");
	private double cameraWidth = Double.parseDouble(properties.getString("camerawidth"));
	private double cameraHeight = Double.parseDouble(properties.getString("cameraheight"));
	private int camerarowN = Integer.parseInt(properties.getString("camerarowN"));
	
	public CameraPanel() {
		myGridPane = new GridPane();
		myGridPane.setPrefWidth(cameraWidth);
		myGridPane.setPrefHeight(cameraHeight);
		
		myArea = new VBox(myGridPane, buttonRow());
		myArea.setSpacing(5);
		
		setGrid();
		
	}
	
	private HBox buttonRow() {
		myPlay = new Button(PLAY);
		myPause = new Button(PAUSE);
		HBox buttonRow = new HBox(myPlay, myPause);
		buttonRow.setSpacing(cameraWidth/5);
		
		return buttonRow;
	}

	private void setGrid() {
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
		return myArea;
	}

	@Override
	public int getArea() {
		return Screen.CAMERA;
	}

	@Override
	public void setController() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * get play button
	 * @return play button
	 */
	public Button getPlay() {
		return myPlay;
	}
	
	/**
	 * get pause button
	 * 
	 * @return pause button
	 */
	public Button getPause() {
		return myPause;
	}
	
	/**
	 * get gridpane
	 * @return gridpane
	 */
	public GridPane getGridPane() {
		return myGridPane;
	}

}
