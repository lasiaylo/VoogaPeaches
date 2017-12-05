package authoring.panels.tabbable;


import authoring.IPanelController;
import authoring.Panel;
import database.filehelpers.FileDataFolders;
import database.filehelpers.FileDataManager;
import engine.managers.EntityManager;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import util.PropertiesReader;
import util.math.num.Vector;


import java.io.InputStream;


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
        FileDataManager manager = new FileDataManager(FileDataFolders.IMAGES);

        myEntType.getItems().addAll(manager.getSubFolder());
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
        FileDataManager manager = new FileDataManager(FileDataFolders.IMAGES);
        for(InputStream imageStream : manager.retrieveSubfolderFiles(type)) {
            Image image = new Image(imageStream);
            ImageView view = new ImageView(image);
            view.setFitWidth(50);
            view.setFitHeight(50);
            myTilePane.getChildren().add(view);
            if (type.equals(BG)) {
                view.setOnMouseClicked(e -> myManager.setMyBGType(imageStream));
            }
            else {
                //view.setOnMouseClicked(e -> myManager.addNonBG(new Vector(25, 25), imageStream));
                view.setOnDragDetected(e -> startDrag(e, image, view));
            }
        }
    }

    private void startDrag(MouseEvent event, Image image, ImageView view) {
        Dragboard board = view.startDragAndDrop(TransferMode.COPY);
        ClipboardContent content = new ClipboardContent();
        content.putImage(image);
        board.setContent(content);
        event.consume();
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
