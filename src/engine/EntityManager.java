package engine;

import database.ObjectFactory;
import database.filehelpers.FileDataFolders;
import database.filehelpers.FileDataManager;
import engine.entities.Entity;
import engine.events.*;
import engine.events.MouseDragEvent;
import engine.util.FXProcessing;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import util.ErrorDisplay;
import util.exceptions.ObjectBlueprintNotFoundException;
import util.math.num.Vector;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class EntityManager {
    private Entity root;
    private Map<String, Entity> levels;
    private Entity currentLevel;
    private int[] mode = {-1};
    private InputStream BGType;
    private int grid;
    private FileDataManager manager;
    private Vector startPos = new Vector(0, 0);
    private Vector startSize = new Vector(0, 0);
    private Vector startPosBatch = new Vector(0, 0);
    private ObjectFactory objectFactory;


    public EntityManager(Entity root, int gridSize) {
        this.root = root;
        this.levels = new HashMap<>();
        this.grid = gridSize;

        manager = new FileDataManager(FileDataFolders.IMAGES);
        BGType = manager.readFileData("Background/grass.png");
        try {
            objectFactory = new ObjectFactory("BGEntity");
        } catch (ObjectBlueprintNotFoundException e) {
            e.printStackTrace();
        }

        //don't freak out about this..... just a initial level
        addLevel("level 1", 5000, 5000);
        currentLevel = levels.get("level 1");
        for(String key : levels.keySet()) {
            Entity entity = levels.get(key);
            entity.getNodes().setOnKeyPressed(e -> new KeyPressEvent(e.getCode()).fire(entity));
        }
    }

    /**
     * add background block from the current selected BGType
     * BGtype is stored as a field inside manager, can be changed by library panel calling setBGType
     * @param pos
     */
    public void addBG(Vector pos) {
        if (mode[0] == 0) {
            //todo this should be replaced by object factory
            Entity BGblock = objectFactory.newObject();
            BGblock.addTo(currentLevel.getChildren().get(0));
            try {
                BGType.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ImageViewEvent imgEvent = new ImageViewEvent(new Image(BGType));
            InitialImageEvent iEvent = new InitialImageEvent(grid, pos);
            ClickEvent cEvent = new ClickEvent(false, mode, BGType);
            KeyPressEvent pEvent = new KeyPressEvent(KeyCode.BACK_SPACE, false);

            imgEvent.fire(BGblock);
            iEvent.fire(BGblock);
            cEvent.fire(BGblock);
            pEvent.fire(BGblock);
        }
    }

//    /**
//     * add nonBG entity through inputstream
//     * @param pos
//     * @param image
//     */
//    public void addNonBG(Vector pos, InputStream image) {
//        try {
//            image.reset();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Image img = new Image(image);
//        addNonBG(pos, img);
//    }

    /**
     * add nonBG entity thru image (for drag and drop)
     * @param pos
     */
    public void addNonBG(Vector pos, String entType) {
        if (mode[0] > 0) {
            if (mode[0] > currentLevel.getChildren().size() - 1) {
                addLayer();
            }
            try {
                ObjectFactory nonBGFactory = new ObjectFactory(entType);
                Entity newEnt = objectFactory.newObject();
                newEnt.addTo(currentLevel.getChildren().get(mode[0]));

                //should not set the image for imageview cuz the type should set the image
                InitialImageEvent iEvent = new InitialImageEvent(grid, pos);
                //the BGType here should not be applied to the image, mode should check for it
                ClickEvent cEvent = new ClickEvent(false, mode, BGType);
                KeyPressEvent pEvent = new KeyPressEvent(KeyCode.BACK_SPACE, false);
                MousePressEvent mEvent = new MousePressEvent(startPos, startSize, false, mode);
                MouseDragEvent dEvent = new MouseDragEvent(startPos, startSize, false, mode);

                iEvent.fire(newEnt);
                cEvent.fire(newEnt);
                pEvent.fire(newEnt);
                mEvent.fire(newEnt);
                dEvent.fire(newEnt);
            } catch (ObjectBlueprintNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * change background type for clicking
     * @param type
     */
    public void setMyBGType (InputStream type) {
        BGType = type;
        ClickEvent cEvent = new ClickEvent(false, mode, BGType);

    }


    /**
     * select BG layer
     */
    public void selectBGLayer() {
        selectLayer(0);
    }


    /**
     * select any layer
     * @param layer
     */
    public void selectLayer(int layer) {
        mode[0] = layer;
        currentLevel.getChildren().forEach(e -> deselect(e));

        select(currentLevel.getChildren().get(layer));
    }

    /**
     * select all layer
     */
    public void allLayer() {
        mode[0] = -1;
        currentLevel.getChildren().forEach(e -> viewOnly(e));
    }


    /**
     * clear entities on current layer
     */
    public void clearOnLayer() {
        if (mode[0] == 0) {
            currentLevel.getChildren().get(0).clearLayer();
        }
        else if(mode[0] == -1) {
            currentLevel.getChildren().forEach(e -> e.clearLayer());
        }
        else {
            currentLevel.getChildren().get(mode[0]).clearLayer();
        }
    }

    private void select(Entity layer) {
        TransparentMouseEvent viewTrans = new TransparentMouseEvent(false);
        ViewVisEvent viewVis = new ViewVisEvent(true);

        layer.getChildren().forEach(e -> {
            viewTrans.fire(e);
            viewVis.fire(e);
        });
    }

    private void deselect(Entity layer) {
        TransparentMouseEvent viewTrans = new TransparentMouseEvent(true);
        ViewVisEvent viewVis = new ViewVisEvent(false);
        layer.getChildren().forEach(e -> {
            viewTrans.fire(e);
            viewVis.fire(e);
        });
    }

    private void viewOnly(Entity layer) {
        TransparentMouseEvent viewTrans = new TransparentMouseEvent(true);
        ViewVisEvent viewVis = new ViewVisEvent(true);
        layer.getChildren().forEach(e -> {
            viewTrans.fire(e);
            viewVis.fire(e);
        });
    }

    /**
     * add layer to current level
     */
    public void addLayer() {
        addLayer(currentLevel);
    }

    private void addLayer(Entity level) {
        Entity layer = new Entity(level);
        ImageView holder = setPlaceHolder();
        layer.add(holder);
        AddLayerEvent addLayer = new AddLayerEvent(layer);
        addLayer.fire(level);
    }

    private ImageView setPlaceHolder() {
        ImageView holder = new ImageView(new Image(manager.readFileData("holder.gif")));
        holder.setX(0);
        holder.setY(0);
        holder.setFitWidth(grid);
        holder.setFitHeight(grid);
        holder.setMouseTransparent(true);
        return holder;
    }

    /**
     * add new level
     * @param name
     * @param mapWidth
     * @param mapHeight
     */
    public void addLevel(String name, int mapWidth, int mapHeight) {
        Entity level = new Entity(root);
        //somehow fucking add the name to level properties
        levels.put(name, level);
        Canvas canvas = new Canvas(mapWidth, mapHeight);
        StackPane stack = new StackPane();
        stack.getChildren().add(canvas);

        canvas.setOnMouseClicked(e -> addBGCenter(new Vector(e.getX(), e.getY()), e));
        canvas.setOnMousePressed(e -> startDragBatch(e));
        canvas.setOnMouseReleased(e -> addBatch(e, startPosBatch));

        stack.setOnDragOver(e -> dragOver(e, stack));
        stack.setOnDragDropped(e -> dragDropped(e));

        level.on(EventType.ADDLAYER.getType(), event -> {
            AddLayerEvent addLayer = (AddLayerEvent) event;
            stack.getChildren().add(addLayer.getLayerGroup());
            stack.setAlignment(addLayer.getLayerGroup(), Pos.TOP_LEFT);
        });
        level.add(stack);

        addLayer(level);
    }

    private void dragOver(DragEvent event, Node map) {
        if (event.getGestureSource() != map && event.getDragboard().hasImage()) {
            event.acceptTransferModes(TransferMode.COPY);
        }
        event.consume();
    }

    private void dragDropped(DragEvent event) {
        Dragboard board = event.getDragboard();
        if (board.hasString()) {
            addNonBG(new Vector(event.getX(), event.getY()), board.getString());
        }
        event.setDropCompleted(true);
        event.consume();
    }

    private void startDragBatch(MouseEvent event) {
        startPosBatch = new Vector(event.getX(), event.getY());
        event.consume();
    }

    private void addBatch(MouseEvent event, Vector start) {
        Vector end = new Vector(event.getX(), event.getY());
        Vector startC = FXProcessing.getBGCenter(start, grid);
        Vector endC = FXProcessing.getBGCenter(end, grid);
        for (double i = startC.at(0); i <= endC.at(0); i += grid) {
            for (double j = startC.at(1); j <= endC.at(1); j += grid) {
                Vector center = FXProcessing.getBGCenter(new Vector(i, j), grid);
                addBG(center);
            }
        }
        event.consume();
    }

    private void addBGCenter(Vector pos, MouseEvent event) {
        Vector center = FXProcessing.getBGCenter(pos, grid);
        addBG(center);
        event.consume();
    }


    /**
     * Change current level
     *
     * @param level: new level
     */
    public void changeLevel(String level) {
        if (!levels.containsKey(null))
            new ErrorDisplay("Level Doesn't Exist", "Oops 😧 !! Level " + level + " does not exist");
        else
            currentLevel = levels.get(level);
    }


    public Entity getCurrentLevel() {
        return currentLevel;
    }

    public Entity getRoot() {
        return root;
    }

}
