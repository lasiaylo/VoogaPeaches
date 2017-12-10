package engine;

import database.ObjectFactory;
import database.filehelpers.FileDataFolders;
import database.filehelpers.FileDataManager;
import database.firebase.TrackableObject;
import engine.camera.Camera;
import engine.entities.Entity;
import engine.events.*;
import engine.events.MouseDragEvent;
import engine.util.FXProcessing;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
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
    private int mode = -1;
    private String BGType;
    private int grid;
    private FileDataManager manager;
    private Vector startPos = new Vector(0, 0);
    private Vector startSize = new Vector(0, 0);
    private Vector startPosBatch = new Vector(0, 0);
    private ObjectFactory BGObjectFactory;
    private ObjectFactory defaultObjectFactory;
    private ObservableMap<String, Vector> levelSize;
    private Camera camera;
    private String currentLevelName;


    public EntityManager(Entity root, int gridSize) {
        this.root = root;
        this.levels = new HashMap<>();
        this.grid = gridSize;
        this.levelSize = FXCollections.observableMap(new HashMap<>());

        manager = new FileDataManager(FileDataFolders.IMAGES);
        BGType = "Background/grass.png";
        try {
            BGObjectFactory = new ObjectFactory("BGEntity");
            defaultObjectFactory = new ObjectFactory("PlayerEntity");

        } catch (ObjectBlueprintNotFoundException e) {
            e.printStackTrace();
        }

        //don't freak out about this..... just a initial level
        addLevel("level 1", 5000, 5000);
        currentLevel = levels.get("level 1");
        currentLevelName = "level 1";
        for(String key : levels.keySet()) {
            Entity entity = levels.get(key);
            entity.getNodes().setOnKeyPressed(e -> new KeyPressEvent(e).fire(entity));
        }
    }

    public void setCamera(Camera c) {
        camera = c;
    }

    /**
     * add background block from the current selected BGType
     * BGtype is stored as a field inside manager, can be changed by library panel calling setBGType
     * @param pos
     */
    public void addBG(Vector pos) {
        if (mode == 0) {
            Entity BGblock = BGObjectFactory.newObject();
            BGblock.addTo(currentLevel.getChildren().get(0));

            new ImageViewEvent(BGType).fire(BGblock);
            new InitialImageEvent(grid, pos).fire(BGblock);
            new ClickEvent(false, mode, BGType).fire(BGblock);
            new KeyPressEvent(KeyCode.BACK_SPACE, false).fire(BGblock);
        }
    }

    /**
     * add nonbg user-defined entity
     * @param pos
     * @param uid
     */
    public void addNonBG(Vector pos, String uid) {
        Entity entity = (Entity) TrackableObject.objectForUID(uid);
        addNonBGPrivate(pos, entity);
    }

    private void addNonBGPrivate(Vector pos, Entity entity) {
        if (mode > 0) {
            if (mode > currentLevel.getChildren().size() - 1) {
                addLayer();
            }
<<<<<<< HEAD
            entity.addTo(currentLevel.getChildren().get(mode));

            InitialImageEvent iEvent = new InitialImageEvent(grid, pos);
=======
            entity.addTo(currentLevel.getChildren().get(mode[0]));
            entity.setProperty("x", pos.at(0));
            entity.setProperty("y", pos.at(1));
            new InitialImageEvent(grid, pos).fire(entity);
>>>>>>> 3032c6309cf8c328b5ae656188f89e19b3cd1d2f
            //the BGType here should not be applied to the image, mode should check for it
            new ClickEvent(false, mode, BGType).fire(entity);
            new KeyPressEvent(KeyCode.BACK_SPACE, false).fire(entity);
            new MouseDragEvent(false, mode).fire(entity);
        }
    }


    /**
     * change background type for clicking
     * @param type
     */
    public void setMyBGType (String type) {
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
        mode = layer;
        currentLevel.getChildren().forEach(e -> deselect(e));
        viewOnly(currentLevel.getChildren().get(0));
        select(currentLevel.getChildren().get(layer));
    }

    /**
     * select all layer
     */
    public void allLayer() {
        mode= -1;
        currentLevel.getChildren().forEach(e -> viewOnly(e));
    }

    /**
     * clear entities on current layer
     */
    public void clearOnLayer() {
        if (mode == 0) {
            currentLevel.getChildren().get(0).clearLayer();
        }
        else if(mode == -1) {
            currentLevel.getChildren().forEach(e -> e.clearLayer());
        }
        else {
            currentLevel.getChildren().get(mode).clearLayer();
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

    public void deleteLayer() {
        if (mode > 0) {
            currentLevel.remove(currentLevel.getChildren().get(mode));
        }
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
        if (levels.containsKey(name)) {
            new ErrorDisplay("Level Name", "Level name already exists").displayError();
            return;
        }
        Entity level = new Entity(root);
        //somehow fucking add the name to level properties
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
        level.getNodes().getChildren().add(stack);
        levels.put(name, level);
        levelSize.put(name, new Vector(mapWidth, mapHeight));

        addLayer(level);
    }

    private void dragOver(DragEvent event, Node map) {
        if (event.getGestureSource() != map && (event.getDragboard().hasImage() || event.getDragboard().hasString())) {
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
        if (!levels.containsKey(level)) {
            new ErrorDisplay("Level Doesn't Exist", "Oops 😧 !! Level " + level + " does not exist").displayError();
            return;
        }
        if (currentLevel.equals(levels.get(level))) {
            return;
        }
        currentLevel = levels.get(level);
        currentLevelName = level;
        camera.changeLevel(currentLevel);
    }


    public Entity getCurrentLevel() {
        return currentLevel;
    }

    public String getCurrentLevelName() {
        return currentLevelName;
    }

    public Entity getRoot() {
        return root;
    }

    public void addMapListener(MapChangeListener<String, Vector> listener) {
        levelSize.addListener(listener);
    }

    public void changeLevelName(String oldName, String newName) {
        if (oldName.equals(currentLevelName)) {
            currentLevelName = newName;
        }
        Entity ent = levels.get(oldName);
        levels.remove(oldName);
        levels.put(newName, ent);
        Vector temp = levelSize.get(oldName);
        levelSize.remove(oldName);
        levelSize.put(newName, temp);
    }

    public void deleteLevel(String name) {
        levels.remove(name);
        levelSize.remove(name);
    }
}
