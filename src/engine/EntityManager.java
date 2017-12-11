package engine;

import database.ObjectFactory;
import database.filehelpers.FileDataFolders;
import database.filehelpers.FileDataManager;
import database.firebase.TrackableObject;
import engine.camera.Camera;
import engine.camera.NewCamera;
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
import util.pubsub.PubSub;
import util.pubsub.messages.BGMessage;
import util.pubsub.messages.NonBGMessage;

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
    private ObjectFactory layerFactory;
    private ObjectFactory levelFactory;
    private ObservableMap<String, Vector> levelSize;
    private NewCamera camera;
    private String currentLevelName;
    private boolean isGaming;


    public EntityManager(Entity root, int gridSize, boolean gaming) {
        this.root = root;
        this.levels = new HashMap<>();
        this.grid = gridSize;
        this.isGaming = gaming;
        this.levelSize = FXCollections.observableMap(new HashMap<>());

        manager = new FileDataManager(FileDataFolders.IMAGES);
        BGType = "Background/grass.png";
        PubSub.getInstance().subscribe("ADD_BG", message -> {
            BGMessage bgMessage = (BGMessage) message;
            addBG(bgMessage.getPos());
        });

        PubSub.getInstance().subscribe("ADD_NON_BG", message -> {
            NonBGMessage nonBGMessage = (NonBGMessage) message;
            addNonBG(nonBGMessage.getPos(), nonBGMessage.getUID());
        });

        try {
            BGObjectFactory = new ObjectFactory("BGEntity");
            layerFactory = new ObjectFactory("layer");
            levelFactory = new ObjectFactory("level");

        } catch (ObjectBlueprintNotFoundException e) {
            e.printStackTrace();
        }
        if (root.getChildren().isEmpty()) {
            //don't freak out about this..... just a initial level
            addLevel("level 1", 5000, 5000);
            currentLevel = levels.get("level 1");
            currentLevelName = "level 1";
        }
        else {
            root.getChildren().forEach(e -> {
                levels.put((String) e.getProperty("levelname"), e);
                levelSize.put((String) e.getProperty("levelname"), new Vector((double) e.getProperty("mapwidth"), (double) e.getProperty("mapheight")));
            });
            currentLevel = root.getChildren().get(0);
            currentLevelName = (String) currentLevel.getProperty("levelname");
        }
    }

    public void setCamera(NewCamera c) {
        camera = c;
    }

    /**
     * add background block from the current selected BGType
     * BGtype is stored as a field inside manager, can be changed by library panel calling setBGType
     * @param pos
     */
    public void addBG(Vector pos) {
        if (mode == 0 && !isGaming) {
            Entity BGblock = BGObjectFactory.newObject();
            BGblock.addTo(currentLevel.getChildren().get(0));

            new InitialImageEvent(new Vector(grid, grid), pos).fire(BGblock);
            new ImageViewEvent(BGType).fire(BGblock);
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
        if (mode > 0 && !isGaming) {
            if (mode > currentLevel.getChildren().size() - 1) {
                addLayer();
            }
            entity.addTo(currentLevel.getChildren().get(mode));
            new InitialImageEvent(new Vector(grid, grid), pos).fire(entity);
            //the BGType here should not be applied to the image, mode should check for it
        }
    }


    /**
     * change background type for clicking
     * @param type
     */
    public void setMyBGType (String type) {
        BGType = type;
//        ClickEvent cEvent = new ClickEvent(false, mode, BGType);
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
        Entity layer = layerFactory.newObject();
        layer.addTo(level);
//        layer.setProperty("gridsize", grid);
//        layer = layer.substitute();
        AddLayerEvent addLayer = new AddLayerEvent(layer);
        addLayer.fire(level);
    }

//    private ImageView setPlaceHolder() {
//        ImageView holder = new ImageView(new Image(manager.readFileData("holder.gif")));
//        holder.setX(0);
//        holder.setY(0);
//        holder.setFitWidth(grid);
//        holder.setFitHeight(grid);
//        holder.setMouseTransparent(true);
//        return holder;
//    }

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
        Entity level = levelFactory.newObject();
        level.addTo(root);
//        level.setProperty("gridsize", grid);
//        level.setProperty("mapwidth", mapWidth);
//        level.setProperty("mapheight", mapHeight);
//        level = level.substitute();
//        new MouseDragEvent(isGamingg).fire(level);
        new MapSetupEvent().fire(level);
        levels.put(name, level);
        levelSize.put(name, new Vector(mapWidth, mapHeight));
        level.setProperty("levelname", name);
        level.setProperty("mapwidth", mapWidth);
        level.setProperty("mapheight", mapHeight);

        addLayer(level);
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
    public void setIsGaming(boolean gaming) {
        isGaming = gaming;
    }
 }
