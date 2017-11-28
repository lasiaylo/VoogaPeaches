package authoring.panels.reserved;

import java.util.ResourceBundle;

import authoring.IPanelDelegate;
import authoring.Panel;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
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
public class CameraPanel implements Panel {
	private static final String PLAY = "Play";
	private static final String PAUSE = "Pause";
	private static final String ALLL = "All Layers";
	private static final String BGL = "Background Layer";
	private static final String NEWL = "Add New Layer";
	private static final String WHOLEB = "Whole Map View";
	private static final String LOCALB = "Local View";

	private static final double SPACING = 10;

	private GridPane myGridPane;
	private Button myPlay;
	private Button myPause;
	private VBox myArea;
	private ChoiceBox<String> myLayer;
	private RadioButton myWhole;
	private RadioButton myLocal;
	private ToggleGroup myGroup;

	private ResourceBundle properties = ResourceBundle.getBundle("screenlayout");
	private double cameraWidth;
	private double cameraHeight;
	private int camerarowN = Integer.parseInt(properties.getString("camerarowN"));
	private String nodeStyle = properties.getString("nodeStyle");
	private IPanelDelegate controller;

	public CameraPanel(double width, double height) {
		cameraWidth = width;
		cameraHeight = height;

		myGridPane = new GridPane();
		myGridPane.setPrefWidth(cameraWidth);
		myGridPane.setPrefHeight(cameraHeight);

		myArea = new VBox(myGridPane, buttonRow());
		myArea.setSpacing(5);
		myArea.setPrefWidth(cameraWidth + SPACING);
		myArea.setPadding(new Insets(5));

		setGrid();

	}

	private HBox buttonRow() {
		myPlay = new Button(PLAY);
		myPause = new Button(PAUSE);
		myLayer = new ChoiceBox<String>();
		myGroup = new ToggleGroup();
		myWhole = new RadioButton(WHOLEB);
		myLocal = new RadioButton(LOCALB);

		setupButton();

		HBox buttonRow = new HBox(myPlay, myPause, myLayer, myWhole, myLocal);
		buttonRow.setPrefWidth(cameraWidth);
		buttonRow.setSpacing(cameraWidth/15);

		return buttonRow;
	}

	private void setupButton() {
		myLayer.getItems().addAll(ALLL, BGL, NEWL);
		myLayer.getSelectionModel().selectFirst();
		myLayer.setStyle(nodeStyle);

		myPlay.setStyle(nodeStyle);
		myPause.setStyle(nodeStyle);

		myWhole.setToggleGroup(myGroup);
		myLocal.setToggleGroup(myGroup);
		myWhole.setSelected(true);
		myWhole.setStyle(nodeStyle);
		myLocal.setStyle(nodeStyle);

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
	public void setController(IPanelDelegate controller) {
		this.controller = controller;
		controller.addCamera(this);
	}

	@Override
	public String title(){
		return "Game Camera";
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

	/**
	 * get layer choicebox
	 * @return choicebox
	 */
	public ChoiceBox<String> getLayer() {
		return myLayer;
	}

	/**
	 * get whole button
	 * @return myWhole
	 */
	public RadioButton getWhole() {
		return myWhole;
	}

	/**
	 * get local button
	 * @return myLocal
	 */
	public RadioButton getLocal() {
		return myLocal;
	}
}
