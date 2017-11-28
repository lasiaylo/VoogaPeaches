package authoring.panels.tabbable;


import authoring.IPanelController;
import authoring.Panel;
import engine.managers.EntityManager;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import util.math.num.Vector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class LibraryPanel implements Panel {
    private static final String BG = "Background";
    private static final String PLAYER = "Player";
    private static final String PATH = "resources/graphics/";

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
        myTilePane.setPrefColumns(2);
        myTilePane.setPrefTileWidth(50);
        myTilePane.setPrefTileHeight(50);
        myTilePane.setHgap(10);

        myArea = new VBox(myEntType, myTilePane);
        myArea.setSpacing(10);
    }

    private void changeType() {
        String type = myEntType.getValue();
        List<ImageView> imageList = new ArrayList<ImageView>();

        try {
            File imageFolder = new File(PATH + type);
            for (File each: imageFolder.listFiles()) {
                ImageView view = new ImageView(new Image(new FileInputStream(each)));
                view.setFitWidth(50);
                view.setFitHeight(50);
                imageList.add(view);
                view.setOnMouseClicked(e -> myManager.addNonStatic(new Vector(25, 25), 1));
            }
        }
        catch (NullPointerException e){
            System.out.println(e);
        }
        catch (FileNotFoundException e){
            System.out.println("This should never happen but...");
        }
        myTilePane.getChildren().clear();
        myTilePane.getChildren().addAll(imageList);
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
