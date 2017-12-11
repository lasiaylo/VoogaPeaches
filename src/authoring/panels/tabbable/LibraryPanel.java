package authoring.panels.tabbable;


import authoring.Panel;
import authoring.PanelController;
import database.ObjectFactory;
import database.filehelpers.FileDataFolders;
import database.filehelpers.FileDataManager;
import engine.EntityManager;
import engine.entities.Entity;
import engine.events.ImageViewEvent;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import util.exceptions.ObjectBlueprintNotFoundException;

import java.io.InputStream;
import java.util.Map;


/**
 * used to store all the different entities that can be used in the authoring environment
 * the backgrounds are toggle buttons that are placed on click in the camera, dragging in an area in the camera also fills those tiles
 * NOTE: only the background is tile based
 * the other entities are dragged onto the camera panel
 */
public class LibraryPanel implements Panel {
    private static final String BG = "Background";
    private static final String PLAYER = "User-Defined";

    private TilePane myTilePane;
    private ChoiceBox<String> myEntType;
    private VBox myArea;
    private PanelController myController;
    private EntityManager myManager;
    private ObjectFactory factory;
    private ObjectFactory defaultFactory;
    private FileDataManager manager;
    private Button update;
    private String type;

    public LibraryPanel() {
        myTilePane = new TilePane();
        myEntType = new ChoiceBox<>();
        manager = new FileDataManager(FileDataFolders.IMAGES);
        try {
            defaultFactory = new ObjectFactory("PlayerEntity");
            factory = new ObjectFactory("PlayerEntity");
        } catch (ObjectBlueprintNotFoundException e) {
            e.printStackTrace();
        }

        myEntType.getItems().addAll(manager.getSubFolder());
        myEntType.getItems().add(PLAYER);
        myEntType.setOnAction(e -> changeType());
        myTilePane.setPrefColumns(2);
        myTilePane.setPrefTileWidth(50);
        myTilePane.setPrefTileHeight(50);
        myTilePane.setHgap(10);
        update = new Button("update");
        update.setOnMouseClicked(e -> changeType());
        myArea = new VBox(myEntType, myTilePane);
        HBox top = new HBox(myEntType, update);
        top.setSpacing(10);
        myArea = new VBox(top, myTilePane);
        myArea.setSpacing(10);

        getRegion().getStyleClass().add("panel");
    }

    private void changeType() {
        type = myEntType.getValue();
        myTilePane.getChildren().clear();
        if (type.equals(PLAYER)) {
            for (String each : ObjectFactory.getEntityTypes()) {
                try {
                    factory.setObjectBlueprint("user_defined/" + each);
                } catch (ObjectBlueprintNotFoundException e) {
                    e.printStackTrace();
                }
                Entity entity = factory.newObject();
                String path = (String) ((Map) ((Map) entity.getProperty("scripts")).getOrDefault("imageScript", null)).getOrDefault("image_path", null);
                Image image = new Image(manager.readFileData(path));
                ImageView view = new ImageView(image);
                view.setFitWidth(50);
                view.setFitHeight(50);
                myTilePane.getChildren().add(view);
                view.setOnDragDetected(e -> startDragEnt(e, view, path, factory));
            }
        } else {
            ToggleGroup backgroundButtons = new ToggleGroup();
            for (String each : manager.getSubFile(type)) {
                InputStream imageStream = manager.readFileData(type + "/" + each);
                Image image = new Image(imageStream);
                ImageView view = new ImageView(image);

                if (type.equals(BG)) {
                    view.setFitWidth(30);
                    view.setFitHeight(30);

                    ToggleButton libraryButton = new ToggleButton();
                    libraryButton.setGraphic(view);

                    libraryButton.setToggleGroup(backgroundButtons);
                    myTilePane.getChildren().add(libraryButton);
                    libraryButton.selectedProperty().addListener((p, ov, nv) -> {
                        if (p.getValue()) {
                            myManager.setMyBGType(type + "/" + each);
                        }
                        else {
                            myManager.setMyBGType("");
                        }
                    });
                } else {
                    view.setFitWidth(50);
                    view.setFitHeight(50);
                    myTilePane.getChildren().add(view);
                    view.setOnDragDetected(e -> startDragEnt(e, view, type + "/" + each, defaultFactory));
                }
            }
        }
    }

    private void startDragEnt(MouseEvent event, ImageView view, String path, ObjectFactory fact) {
        Entity entity = fact.newObject();
        ImageViewEvent iEvent = new ImageViewEvent(path);
        iEvent.fire(entity);
        Dragboard board = view.startDragAndDrop(TransferMode.COPY);
        ClipboardContent content = new ClipboardContent();
        content.putString(entity.UIDforObject());
        board.setContent(content);
        event.consume();
    }


    @Override
    public Region getRegion() {
        return myArea;
    }

    @Override
    public void setController(PanelController controller) {
        myController = controller;
        myManager = myController.getManager();
    }

    @Override
    public String title(){
        return "Library";
    }

}