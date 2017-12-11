package authoring.panels.reserved;

import authoring.Panel;
import authoring.PanelController;
import engine.EntityManager;
import engine.entities.Entity;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import main.VoogaPeaches;
import util.PropertiesReader;
import util.pubsub.PubSub;
import util.pubsub.messages.StringMessage;

/**
 * camera panel inside authoring environment that displays the game
 * this holds a map of the entire level (continuous) and can be scrolled through
 * Note: separate levels/non-contiguous parts of the map must be on separate levels
 * @author estellehe
 * @author Kelly Zhang
 *
 */
public class CameraPanel implements Panel {

    private static final String PLAY = "Play";
	private static final String PAUSE = "Pause";
	private static final String ALLL = "All Layers";
	private static final String BGL = "Background Layer";
	private static final String NEWL = "Add New Layer";
	private static final String LAYER = "Layer ";
	private static final String TEXT = "Layer Name";
	private static final String CLEAR = "Clear";
	private static final double GRIDS = 50;
	private static final double SPACING = 10;
    private static final String CAMERA = "camera";
    private static final String PANEL = "panel";
    private static final String TEXT_FIELD = "textField";
    private static final String DELETE_LAYER = "Delete Layer";
    private static final int CAMERA_WIDTH_RATIO = 30;
    private static final String CHOICE_BOX = "choice-box";
    private static final String GAME_CAMERA = "Game Camera";
    private static final String SCREENLAYOUT = "screenlayout";
    private static final String NODE_STYLE = "nodeStyle";

    private ScrollPane myView;
	private Button myPlay;
	private Button myPause;
	private Button myClear;
	private Button myDelete;
	private VBox myArea;
	private PubSub pubSub;
	private EntityManager myManager;
	private TextField myText;
	private ComboBox<String> myLayer;
	private double cameraWidth;
	private double cameraHeight;
	private int layerC = 1;
	private String myOption;
	private PanelController myController;

	public CameraPanel(double width, double height) {
		cameraWidth = width;
		cameraHeight = height;

		myView = new ScrollPane();
		myView.setPrefWidth(width);
		myView.setPrefHeight(height);

		myArea = new VBox(myView, buttonRow());
		myArea.setSpacing(5);
		myArea.setPrefWidth(cameraWidth + SPACING);
		myArea.setPadding(new Insets(5));

		myView.getStyleClass().add(CAMERA);
		myArea.getStyleClass().add(PANEL);
	}

	/**
	 * sets up the button row that belongs in the camerapanel
	 * @return HBox with all the buttons for the camera
	 */
	private HBox buttonRow() {
		myPlay = new Button(PLAY);
		myPause = new Button(PAUSE);
		myLayer = new ComboBox<>();
		myText = new TextField(TEXT);
		myClear = new Button(CLEAR);
		myDelete = new Button(DELETE_LAYER);

		setupButton();

		HBox buttonRow = new HBox(myPlay, myPause, myLayer, myText, myClear, myDelete);
		buttonRow.setAlignment(Pos.CENTER);
		buttonRow.setPrefWidth(cameraWidth);
		buttonRow.setSpacing(cameraWidth/CAMERA_WIDTH_RATIO);

		return buttonRow;
	}

	/**
	 * sets the camera in the panel
	 * @param view (the actual camera)
	 */
	private void setView(ScrollPane view) {
		myView = view;
		myArea.getChildren().set(0,myView);
		myView.setMouseTransparent(false);
	}

	/**
	 * adds the action connections to the buttons
	 */
	private void setupButton() {
		myLayer.getItems().addAll(ALLL, BGL, NEWL);
		myLayer.getSelectionModel().selectFirst();
		myLayer.setOnAction(e -> changeLayer());
		myText.setOnKeyPressed(e -> changeName(e.getCode()));

		myPlay.setOnMouseClicked(e -> myController.play());
		myPause.setOnMouseClicked(e -> myController.pause());

		myClear.setOnMouseClicked(e -> myManager.clearOnLayer());
		myDelete.setOnMouseClicked(e -> {
		    myManager.deleteLayer();
		    myLayer.getItems().remove(myLayer.getValue());
		    myLayer.getSelectionModel().clearAndSelect(1);
        });


		myLayer.getStyleClass().add(CHOICE_BOX);
	}

	/**
	 * changes the name of the layer
	 * @param code the key pressed
	 */
	private void changeName(KeyCode code) {
	    if (code.equals(KeyCode.ENTER) && (!myOption.equals(NEWL)) && (!myOption.equals(ALLL)) && (!myOption.equals(BGL))) {
	        myText.commitValue();
	        myLayer.getItems().set(myLayer.getItems().indexOf(myLayer.getValue()), myText.getText());
	        myText.setText(TEXT);
        }
    }

	/**
	 * used to switch between layers (levels/non contiguous) parts of the map
	 */
	private void changeLayer() {
		myOption = myLayer.getValue();
		switch (myOption) {
			case NEWL:
				myManager.addLayer();
				myLayer.getItems().add(myLayer.getItems().size() - 1, LAYER + layerC);
				myLayer.getSelectionModel().clearAndSelect(myLayer.getItems().size() - 2);
				layerC++;
				break;
			case ALLL:
				myManager.allLayer();
				break;
			case BGL:
				myManager.selectBGLayer();
				break;
			default:
				int layer = myLayer.getItems().indexOf(myLayer.getValue()) - 1;
				myManager.selectLayer(layer);
				break;
		}

	}

	@Override
	public Region getRegion() {
		return myArea;
	}

	@Override
	public void setController(PanelController controller) {
		System.out.println("Hehrhweafsdf");
		this.myController = controller;
		this.setView(myController.getCamera());
		myManager = myController.getManager();
	}

	@Override
	public String title(){
		return GAME_CAMERA;
	}
}