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
 * @author Estelle He
 * @author Kelly Zhang
 */
public class LibraryPanel implements Panel {

    private static final String BG = "Background";
    private static final String PLAYER = "User-Defined";
    private static final String PLAYER_ENTITY = "PlayerEntity";
    private static final String CHOICE_BOX = "choice-box";
    private static final String UPDATE = "update";
    public static final String PANEL = "panel";
    private static final int SPACING = 10;
    private static final int HGAP = 10;
    private static final int PREF_COLUMNS = 2;
    private static final int PREF_TILE_WIDTH = 50;
    private static final int PREF_TILE_HEIGHT = 50;
    private static final String USER_DEFINED = "user_defined/";
    private static final String SCRIPTS = "scripts";
    private static final String IMAGESCRIPT = "imageScript";
    private static final String IMAGE_PATH = "image_path";
    private static final int VIEW_FIT_WIDTH = 50;
    private static final int VIEW_FIT_HEIGHT = 50;
    private static final String SLASH = "/";
    private static final String LIBRARY = "Library";

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
            defaultFactory = new ObjectFactory(PLAYER_ENTITY);
            factory = new ObjectFactory(PLAYER_ENTITY);
        } catch (ObjectBlueprintNotFoundException e) {
            e.printStackTrace();
        }

        myEntType.getItems().addAll(manager.getSubFolder());
        myEntType.getItems().add(PLAYER);
        myEntType.setOnAction(e -> changeType());
        myTilePane.setPrefColumns(PREF_COLUMNS);
        myTilePane.setPrefTileWidth(PREF_TILE_WIDTH);
        myTilePane.setPrefTileHeight(PREF_TILE_HEIGHT);
        myTilePane.setHgap(HGAP);
        update = new Button(UPDATE);
        update.setOnMouseClicked(e -> changeType());
        myArea = new VBox(myEntType, myTilePane);
        HBox top = new HBox(myEntType, update);
        top.setSpacing(SPACING);
        myArea = new VBox(top, myTilePane);
        myArea.setSpacing(SPACING);
        getRegion().getStyleClass().add(PANEL);
    }

    private void changeType() {
        type = myEntType.getValue();
        myTilePane.getChildren().clear();
        if (type.equals(PLAYER)) {
            for (String each : ObjectFactory.getEntityTypes()) {
                try {
                    factory.setObjectBlueprint(USER_DEFINED + each);
                } catch (ObjectBlueprintNotFoundException e) {
                    e.printStackTrace();
                }
                Entity entity = factory.newObject();
                String path = (String) ((Map)((Map) entity.getProperty(SCRIPTS)).getOrDefault(IMAGESCRIPT, null)).getOrDefault(IMAGE_PATH, null);

                Image image = new Image(manager.readFileData(path));
                ImageView view = new ImageView(image);
                view.setFitWidth(VIEW_FIT_WIDTH);
                view.setFitHeight(VIEW_FIT_HEIGHT);
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
                            //TODO: THIS MIGHT BREAK SOMETHING BUT TBH I DONT THINK IT DOES because there are no entities linked to this
                        }
                    });
                }
                else {
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
        return LIBRARY;
    }
}