package authoring.panels.reserved;

import java.util.ResourceBundle;

import authoring.IPanelController;
import authoring.Panel;
import engine.Engine;
import engine.camera.Camera;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import util.math.num.Vector;

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

	private static final double SPACING = 10;
	
	private ScrollPane myView;
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
	private int layerC = 1;
	private String nodeStyle = properties.getString("nodeStyle");
    private IPanelController myController;

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

	public void getView(ScrollPane view) {
        myView = view;
        myArea.getChildren().set(0, myView);
    }

	private void setupButton() {
		myLayer.getItems().addAll(ALLL, BGL, NEWL);
		myLayer.getSelectionModel().selectFirst();
		myLayer.setStyle(nodeStyle);
		myLayer.setOnMouseClicked(e -> changeLayer());


		myWhole.setToggleGroup(myGroup);
		myLocal.setToggleGroup(myGroup);
		myWhole.setSelected(true);
		myWhole.setStyle(nodeStyle);
		myLocal.setStyle(nodeStyle);

	}

	private void changeLayer() {
        String option = myLayer.getValue();
        switch (option) {
            case NEWL:
                myLayer.getItems().add(myLayer.getItems().size() - 1, LAYER + layerC);
                layerC++;
                break;
            case ALLL:
                myController.selectLayer(-1);
                break;
            case BGL:
                myController.selectLayer(0);
                break;
            default:
                int layer = Character.getNumericValue(option.charAt(option.length()-1));
                myController.selectLayer(layer);
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
	}

    @Override
    public String title(){
        return "Game Camera";
    }


    public void setCameraView(ScrollPane cameraView){
        myArea.getChildren().set(0,cameraView);
        myView = cameraView;
        myView.setPrefWidth(cameraWidth);
        myView.setPrefHeight(cameraHeight);
    }

}
