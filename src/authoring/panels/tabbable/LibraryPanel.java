package authoring.panels.tabbable;


import authoring.Panel;
import authoring.PanelController;
import database.ObjectFactory;
import database.filehelpers.FileDataFolders;
import database.filehelpers.FileDataManager;
import engine.EntityManager;
import engine.entities.Entity;
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
import util.exceptions.ObjectBlueprintNotFoundException;
import util.math.num.Vector;


import java.io.InputStream;


public class LibraryPanel implements Panel {
    private static final String BG = "Background";
    private static final String PLAYER = "User-Defined";

    private TilePane myTilePane;
    private ChoiceBox<String> myEntType;
    private VBox myArea;
    private PanelController myController;
    private EntityManager myManager;

    public LibraryPanel() {
        myTilePane = new TilePane();
        myEntType = new ChoiceBox<>();
        FileDataManager manager = new FileDataManager(FileDataFolders.IMAGES);

        myEntType.getItems().add(BG);
        myEntType.getItems().add(PLAYER);
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
        if (type.equals(BG)) {
            for(InputStream imageStream : manager.retrieveSubfolderFiles(BG)) {
                Image image = new Image(imageStream);
                ImageView view = new ImageView(image);
                view.setFitWidth(50);
                view.setFitHeight(50);
                myTilePane.getChildren().add(view);
                view.setOnMouseClicked(e -> myManager.setMyBGType(imageStream));
            }
        }
        else {
            FileDataManager datamanager = new FileDataManager(FileDataFolders.IMAGES);
            for (String each: ObjectFactory.getEntityTypes()) {
                try {
                    ObjectFactory factory = new ObjectFactory(each);
                    Entity entity = factory.newObject();
                    ImageView view = new ImageView(new Image(datamanager.readFileData((String) entity.getProperty("image path"))));
                    view.setFitWidth(50);
                    view.setFitHeight(50);
                    myTilePane.getChildren().add(view);
                    view.setOnDragDetected(e -> startDrag(e, each, view));
                } catch (ObjectBlueprintNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void startDrag(MouseEvent event, String entType, ImageView view) {
        Dragboard board = view.startDragAndDrop(TransferMode.COPY);
        ClipboardContent content = new ClipboardContent();
        content.putString(entType);
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