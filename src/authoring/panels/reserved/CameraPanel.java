package authoring.panels.reserved;

import java.util.ResourceBundle;
import java.util.function.Consumer;

import authoring.IPanelController;
import authoring.Panel;
import engine.Engine;
import engine.camera.Camera;
import engine.managers.EntityManager;
import engine.util.FXProcessing;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import util.PropertiesReader;
import util.math.num.Vector;
import util.pubsub.PubSub;
import util.pubsub.messages.Message;
import util.pubsub.messages.ThemeMessage;

import static java.lang.Character.getNumericValue;

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
	private static final String LAYER = "Layer ";

	private static final double GRIDS = 50;
	private static final double SPACING = 10;

	private ScrollPane myView;
	private Button myPlay;
	private Button myPause;
	private VBox myArea;
	private ChoiceBox<String> myLayer;
	private RadioButton myWhole;
	private RadioButton myLocal;
	private ToggleGroup myGroup;
	private PubSub pubSub;
	private EntityManager myManager;

	private double cameraWidth;
	private double cameraHeight;
	private int layerC = 1;
	private String nodeStyle = PropertiesReader.value("screenlayout","nodeStyle");
	private IPanelController myController;

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

		pubSub = PubSub.getInstance();
		pubSub.subscribe(
				PubSub.Channel.THEME_MESSAGE,
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


	private void getView(ScrollPane view) {
		myView = view;
		myArea.getChildren().set(0, myView);
		myView.setMouseTransparent(true);
	}


	private void setupButton() {
		myLayer.getItems().addAll(ALLL, BGL, NEWL);
		myLayer.getSelectionModel().selectFirst();
	//	myLayer.setStyle(nodeStyle);
		myLayer.setOnAction(e -> changeLayer());

		myPlay.setOnMouseClicked(e -> myController.play());
	//	myPlay.setStyle(nodeStyle);
		myPause.setOnMouseClicked(e -> myController.pause());
	//	myPause.setStyle(nodeStyle);

		myWhole.setToggleGroup(myGroup);
		myLocal.setToggleGroup(myGroup);
		myWhole.setSelected(true);
	//	myWhole.setStyle(nodeStyle);
	//	myLocal.setStyle(nodeStyle);
	}

	private void changeLayer() {
		String option = myLayer.getValue();
		switch (option) {
			case NEWL:
				myManager.addLayer();
				myLayer.getItems().add(myLayer.getItems().size() - 1, LAYER + layerC);
				myLayer.getSelectionModel().clearAndSelect(myLayer.getItems().size() - 2);
				layerC++;
				break;
			case ALLL:
				myManager.allLayer();
				myView.setMouseTransparent(true);
				break;
			case BGL:
				myManager.selectBGLayer();
				myView.setMouseTransparent(false);
				break;
			default:
				int layer = Character.getNumericValue(option.charAt(option.length()-1));
				myManager.selectLayer(layer);
				myView.setMouseTransparent(true);
				myManager.setMyLevel(layer);
				break;
		}

	}


	@Override
	public Region getRegion() {
		return myArea;
	}

	@Override
	public void setController(IPanelController controller) {
		this.myController = controller;
		this.getView(myController.getCamera());
		myManager = myController.getManager();
	}

	@Override
	public String title(){
		return "Game Camera";
	}


}
