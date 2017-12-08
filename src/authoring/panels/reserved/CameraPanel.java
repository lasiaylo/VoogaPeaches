package authoring.panels.reserved;

import authoring.Panel;
import authoring.PanelController;
import engine.EntityManager;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import main.VoogaPeaches;
import util.PropertiesReader;
import util.pubsub.PubSub;
import util.pubsub.messages.ThemeMessage;

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
	private static final String LAYER = "Layer ";
	private static final String TEXT = "Layer Name";
	private static final String CLEAR = "Clear";

	private static final double GRIDS = 50;
	private static final double SPACING = 10;

	private ScrollPane myView;
	private Button myPlay;
	private Button myPause;
	private Button myClear;
	private VBox myArea;
	private PubSub pubSub;
	private EntityManager myManager;
	private TextField myText;
	private ComboBox<String> myLayer;
	private RadioButton myWhole;
	private RadioButton myLocal;
	private ToggleGroup myGroup;

	private double cameraWidth;
	private double cameraHeight;
	private int layerC = 1;
	private String myOption;
	private String nodeStyle = PropertiesReader.value("screenlayout","nodeStyle");
	private PanelController myController;

	public CameraPanel(double width, double height) {
		cameraWidth = width;
		cameraHeight = height;

		myView = new ScrollPane();
		myView.getStyleClass().add("camera");
		myView.setPrefWidth(width);
		myView.setPrefHeight(height);

		myArea = new VBox(myView, buttonRow());
		myArea.getStyleClass().add("panel");
		myArea.setSpacing(5);
		myArea.setPrefWidth(cameraWidth + SPACING);
		myArea.setPadding(new Insets(5));
		myArea.getStylesheets().add(VoogaPeaches.getUser().getThemeName());

		pubSub = PubSub.getInstance();
		pubSub.subscribe(
				"THEME_MESSAGE",
				(message) -> updateStyles(myArea, ((ThemeMessage) message).readMessage()));
	}


	private void updateStyles(Region region, String css) {
		if (region.getStylesheets().size() >= 1) {
			region.getStylesheets().remove(0);
		}
		region.getStylesheets().add(css);
	}

	private HBox buttonRow() {
		myPlay = new Button(PLAY);
		myPause = new Button(PAUSE);
		myLayer = new ComboBox<>();
		myText = new TextField(TEXT);
		myText.getStyleClass().add("textField");
		myClear = new Button(CLEAR);

		setupButton();

		HBox buttonRow = new HBox(myPlay, myPause, myLayer, myText, myClear);
		buttonRow.setPrefWidth(cameraWidth);
		buttonRow.setSpacing(cameraWidth/20);

		return buttonRow;
	}


	private void getView(ScrollPane view) {
		myView = view;
		myArea.getChildren().set(0, myView);
		myView.setMouseTransparent(false);
	}


	private void setupButton() {
		myLayer.getItems().addAll(ALLL, BGL, NEWL);
		myLayer.getSelectionModel().selectFirst();
		myLayer.setOnAction(e -> changeLayer());
		myLayer.getStyleClass().add("choice-box");
		myText.setOnKeyPressed(e -> changeName(e.getCode()));

		myPlay.setOnMouseClicked(e -> myController.play());
		myPause.setOnMouseClicked(e -> myController.pause());

		myClear.setOnMouseClicked(e -> myManager.clearOnLayer());

	}

	private void changeName(KeyCode code) {
	    if (code.equals(KeyCode.ENTER) && (!myOption.equals(NEWL)) && (!myOption.equals(ALLL))) {
	        myText.commitValue();
	        myLayer.getItems().set(myLayer.getItems().indexOf(myLayer.getValue()), myText.getText());
        }
    }

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
				int layer = Character.getNumericValue(myOption.charAt(myOption.length()-1));
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
		this.myController = controller;
		this.getView(myController.getCamera());
		myManager = myController.getManager();
	}

	@Override
	public String title(){
		return "Game Camera";
	}


}
