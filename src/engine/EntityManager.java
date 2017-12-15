package engine;

import database.ObjectFactory;
import database.firebase.TrackableObject;
import database.jsonhelpers.JSONDataFolders;
import engine.camera.Camera;
import engine.entities.Entity;
import engine.events.*;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.scene.layout.StackPane;
import util.ErrorDisplay;
import util.math.num.Vector;
import util.pubsub.PubSub;
import util.pubsub.messages.BGMessage;
import util.pubsub.messages.NonBGMessage;

import java.util.HashMap;
import java.util.Map;

public class EntityManager {

    private Entity root;
    private Map<String, Entity> levels;
    private Entity currentLevel;
    private int mode = -1;
    private String BGType;
    private int grid;
    private ObjectFactory BGObjectFactory;
    private ObjectFactory layerFactory;
    private ObjectFactory levelFactory;
    private ObservableMap<String, Vector> levelSize;
    private Camera camera;
    private String currentLevelName;
    private boolean isGaming;


    public EntityManager(Entity root, int gridSize, boolean gaming) {
        this.root = root;
        this.levels = new HashMap<>();
        this.grid = gridSize;
        this.isGaming = gaming;
        this.levelSize = FXCollections.observableMap(new HashMap<>());
        BGType = "";


        setRoot(root);

        //writeRootToDatabase(root);
    }

    public void setRoot(Entity root) {
        this.root = root;
        levels.clear();
        levelSize.clear();
        setupPubSub();
        setupFactories();
        addLevels();
    }

    private void addLevels() {
        if (root.getChildren().isEmpty()) {
            addLevel("level 1", 2000, 2000);
            currentLevel = levels.get("level 1");
            currentLevelName = "level 1";
        } else {
            root.getChildren().forEach(e -> {
                levels.put((String) e.getProperty("levelname"), e);
                levelSize.put((String) e.getProperty("levelname"), new Vector(0.0 + (int) e.getProperty("mapwidth"), 0.0 + (int) e.getProperty("mapheight")));
                for (Entity each: e.getChildren()) {
                    new AddLayerEvent(each).fire(e);
                    recursiveAdd(each);
                }
                new MouseDragEvent(isGaming).fire(e);
                new MapSetupEvent().fire(e);
            });
            currentLevel = root.getChildren().get(0);
            currentLevelName = (String) currentLevel.getProperty("levelname");
        }
    }

    private void setupFactories() {
        BGObjectFactory = new ObjectFactory("BGEntity", JSONDataFolders.DEFAULT_USER_ENTITY);
        layerFactory = new ObjectFactory("layer", JSONDataFolders.DEFAULT_USER_ENTITY);
        levelFactory = new ObjectFactory("level", JSONDataFolders.DEFAULT_USER_ENTITY);
    }

    private void setupPubSub(){
        PubSub.getInstance().subscribe("ADD_BG", message -> {
            BGMessage bgMessage = (BGMessage) message;
            addBG(bgMessage.getPos());
        });

        PubSub.getInstance().subscribe("ADD_NON_BG", message -> {
            NonBGMessage nonBGMessage = (NonBGMessage) message;
            addNonBG(nonBGMessage.getPos(), nonBGMessage.getUID());
        });
    }

    private void recursiveAdd(Entity layer){
        for(int i = 0; i < layer.getChildren().size(); i++){
            layer.getChildren().get(i).addTo(layer);
            recursiveAdd(layer.getChildren().get(i));
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
//        System.out.println(root.getChildren().size());
//        System.out.println(currentLevel.getChildren().get(0));
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
            entity.substitute();
            //new MouseDragEvent(false).fire(entity);
            //the BGType here should not be applied to the image, mode should check for it
        }
    }


    /**
     * change background type for clicking
     * @param type
     */
    public void setMyBGType (String type) { BGType = type; }

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
        if (mode == 0) currentLevel.getChildren().get(0).clearLayer();
        else if(mode == -1) currentLevel.getChildren().forEach(e -> e.clearLayer());
        else currentLevel.getChildren().get(mode).clearLayer();
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
            ((StackPane)currentLevel.getNodes().getChildren().get(0)).getChildren().remove(currentLevel.getChildren().get(mode).getNodes());
            currentLevel.remove(currentLevel.getChildren().get(mode));
            mode = 0;
        }
    }

    private void addLayer(Entity level) {
        Entity layer = layerFactory.newObject();
        layer.addTo(level);
        layer.setProperty("gridsize", grid);
        layer = layer.substitute();
        AddLayerEvent addLayer = new AddLayerEvent(layer);
        addLayer.fire(level);
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
        Entity level = levelFactory.newObject();
        level.addTo(root);
        level.setProperty("gridsize", grid);
        level.setProperty("mapwidth", mapWidth);
        level.setProperty("mapheight", mapHeight);
        level = level.substitute();
        new MouseDragEvent(isGaming).fire(level);
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
    public Entity changeLevel(String level) {
        if (!levels.containsKey(level)) {
            new ErrorDisplay("Level Doesn't Exist", "Oops 😧 !! Level " + level + " does not exist").displayError();
            return currentLevel;
        }
        if (currentLevel.equals(levels.get(level))) {
            camera.changeLevel(currentLevel);
            return currentLevel;
        }
        currentLevel = levels.get(level);
        currentLevelName = level;
        camera.changeLevel(currentLevel);
        return currentLevel;
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
        if (oldName.equals(currentLevelName)) currentLevelName = newName;
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

    public boolean isGaming() { return isGaming; }

    public Map<String, Vector> getMap() {
        return levelSize;
    }
 }
