package authoring.panels.tabbable;


import authoring.IPanelController;
import authoring.Panel;
import database.filehelpers.FileDataManager;
import engine.managers.EntityManager;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import util.PropertiesReader;
import util.math.num.Vector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LibraryPanel implements Panel {
    private static final String BG = "Background";
    private static final String PLAYER = "Player";

    private TilePane myTilePane;
    private ChoiceBox<String> myEntType;
    private VBox myArea;
    private IPanelController myController;
    private EntityManager myManager;

    public LibraryPanel() {
        myTilePane = new TilePane();
        myEntType = new ChoiceBox<>();

        myEntType.getItems().addAll(BG, PLAYER);
        myEntType.setOnAction(e -> changeType());
        myEntType.getStyleClass().add("choice-box");
        myTilePane.setPrefColumns(2);
        myTilePane.setPrefTileWidth(50);
        myTilePane.setPrefTileHeight(50);
        myTilePane.setHgap(10);

        myArea = new VBox(myEntType, myTilePane);
        myArea.getStyleClass().add("panel");
        myArea.setSpacing(10);
    }

    private void changeType() {
        String type = myEntType.getValue();
        myTilePane.getChildren().clear();
        FileDataManager manager = new FileDataManager(FileDataManager.FileDataFolders.IMAGES);
        for(InputStream imageStream : manager.retrieveSubfolderFiles(type)) {
            ImageView view = new ImageView(new Image(imageStream));
            view.setFitWidth(50);
            view.setFitHeight(50);
            myTilePane.getChildren().add(view);
            if (type.equals(BG)) {
                view.setOnMouseClicked(e -> myManager.setMyBGType(imageStream));
            }
            else {
                view.setOnMouseClicked(e -> myManager.addNonStatic(new Vector(25, 25), imageStream));
            }
        }
    }

    @Override
    public Region getRegion() {
        return myArea;
    }

    @Override
    public void setController(IPanelController controller) {
        myController = controller;
        myManager = myController.getManager();
    }

    @Override
    public String title(){
        return "Library";
    }

}
