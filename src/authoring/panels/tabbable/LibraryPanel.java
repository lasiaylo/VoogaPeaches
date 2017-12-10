package authoring.panels.tabbable;


import authoring.Panel;
import authoring.PanelController;
import database.ObjectFactory;
import database.filehelpers.FileDataFolders;
import database.filehelpers.FileDataManager;
import engine.EntityManager;
import engine.entities.Entity;
import engine.events.ImageViewEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import util.exceptions.ObjectBlueprintNotFoundException;

import java.io.InputStream;
import java.util.Map;


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
            for (String each: ObjectFactory.getEntityTypes()) {
                try {
                    factory.setObjectBlueprint("user_defined/" + each);
                } catch (ObjectBlueprintNotFoundException e) {
                    e.printStackTrace();
                }
                Entity entity = factory.newObject();
                String path = (String) ((Map)((Map) entity.getProperty("scripts")).getOrDefault("imageScript", null)).getOrDefault("image_path", null);
                Image image = new Image(manager.readFileData(path));
                ImageView view = new ImageView(image);
                view.setFitWidth(50);
                view.setFitHeight(50);
                myTilePane.getChildren().add(view);
                view.setOnDragDetected(e -> startDragEnt(e, view, path, factory));
            }
        }
        else {
            for (String each: manager.getSubFile(type)) {
                InputStream imageStream = manager.readFileData(type + "/" + each);
                Image image = new Image(imageStream);
                ImageView view = new ImageView(image);
                view.setFitWidth(50);
                view.setFitHeight(50);
                myTilePane.getChildren().add(view);
                if (type.equals(BG)) {
                    view.setOnMouseClicked(e -> myManager.setMyBGType(type + "/" + each));
                }
                else {
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