package engine;

import database.filehelpers.FileDataFolders;
import database.filehelpers.FileDataManager;
import engine.entities.Entity;
import engine.events.*;
import engine.util.FXProcessing;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import util.ErrorDisplay;
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
    private InputStream BGType;
    private int grid;
    private FileDataManager manager;
    private Vector startPos = new Vector(0, 0);
    private Vector startSize = new Vector(0, 0);
    private Vector startPosBatch = new Vector(0, 0);


    public EntityManager(Entity root, int gridSize) {
        this.root = root;
        this.levels = new HashMap<>();
        this.grid = gridSize;

        manager = new FileDataManager(FileDataFolders.IMAGES);
        BGType = manager.readFileData("Background/grass.png");

        //don't freak out about this..... just a initial level
        addLevel("level 1", 5000, 5000);
        if(levels.get("level 1") == null) System.out.println("here");
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
        if (mode == 0) {
            Entity BGblock = new Entity(currentLevel.getChildren().get(0));
            ImageView view = new ImageView();
            changeBGImage(view);
            setupImage(pos, view);
            BGblock.add(view);
            BGblock.setProperty("x", pos.at(0));
            BGblock.setProperty("y", pos.at(1));

            view.setOnMouseClicked(e -> changeRender(e, view));
            view.setOnKeyPressed(e -> deleteEntity(e, BGblock));
        }
    }

    private void changeRender(MouseEvent event, ImageView view) {
        view.requestFocus();
        if (event.getButton().equals(MouseButton.PRIMARY) && mode == 0) {
            changeBGImage(view);
        }
        event.consume();
    }

    private void deleteEntity(KeyEvent event, Entity entity) {
        if (event.getCode().equals(KeyCode.BACK_SPACE)) {
            entity.getParent().remove(entity);
        }
        event.consume();
    }


    private void changeBGImage(ImageView view) {
        try {
            BGType.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        view.setImage(new Image(BGType));
    }


    /**
     * add nonBG entity through inputstream
     * @param pos
     * @param image
     */
    public void addNonBG(Vector pos, InputStream image) {
        try {
            image.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image img = new Image(image);
        addNonBG(pos, img);
    }

    /**
     * add nonBG entity thru image (for drag and drop)
     * @param pos
     * @param image
     */
    public void addNonBG(Vector pos, Image image) {
        if (mode > 0) {
            if (mode > currentLevel.getChildren().size() - 1) {
                addLayer();
            }
            Entity newEnt = new Entity(currentLevel.getChildren().get(mode));
            ImageView view = new ImageView(image);
            setupImage(pos, view);
            newEnt.add(view);
            newEnt.setProperty("x", pos.at(0));
            newEnt.setProperty("y", pos.at(1));

            view.setOnMouseClicked(e -> changeRender(e, view));
            view.setOnKeyPressed(e -> deleteEntity(e, newEnt));
            view.setOnMousePressed(e -> startDrag(e, view));
            view.setOnMouseDragged(e -> drag(e, view, startPos, startSize));
        }
    }


    /**
     * change background type for clicking
     * @param type
     */
    public void setMyBGType (InputStream type) {
        BGType = type;
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

        select(currentLevel.getChildren().get(layer));
    }

    /**
     * select all layer
     */
    public void allLayer() {
        mode = -1;
        currentLevel.getChildren().forEach(e -> viewOnly(e));
    }

    /**
     * change BGType
     * @param image
     */
    public void setBGType(InputStream image) {
        BGType = image;
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

    private void startDrag(MouseEvent event, ImageView view) {
        startPos = new Vector(event.getX(), event.getY());
        startSize = new Vector(view.getFitWidth(), view.getFitHeight());
        event.consume();
    }

    private void drag(MouseEvent event, ImageView view, Vector startPos, Vector startSize) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            move(event, view);
        }
        else if (event.getButton().equals(MouseButton.SECONDARY)) {
            zoom(event, view, startPos, startSize);
        }
    }

    private void zoom(MouseEvent event, ImageView view, Vector startPos, Vector startSize) {
        Vector change = (new Vector(event.getX(), event.getY())).subtract(startPos);
        Vector fsize = change.add(startPos);
        if (event.getX() < startPos.at(0)) {
            change.at(0, 0.0);
        }
        if (event.getY() < startPos.at(1)) {
            change.at(1, 0.0);
        }
        view.setFitWidth(fsize.at(0));
        view.setFitHeight(fsize.at(1));
        event.consume();
    }

    private void move(MouseEvent event, ImageView view) {
        double xPos = event.getX();
        double yPos = event.getY();
        //LOL there is actually a bug here, if you try to drag over the right bound and lower bound
        if (event.getX() < view.getFitWidth()/2) {
            xPos = view.getFitWidth()/2;
        }
        if (event.getY() < view.getFitHeight()/2) {
            yPos = view.getFitHeight()/2;
        }
        view.setX(FXProcessing.getXImageCoord(xPos, view));
        view.setY(FXProcessing.getYImageCoord(yPos, view));
        //need to change the location properties, too lazy to do it
        event.consume();
    }



    private void setupImage(Vector pos, ImageView view) {
        view.setFitWidth(grid);
        view.setFitHeight(grid);
        view.setX(FXProcessing.getXImageCoord(pos.at(0), view));
        view.setY(FXProcessing.getYImageCoord(pos.at(1), view));
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

        level.on("addLayer", event -> {
            AddLayerEvent addLayer = (AddLayerEvent) event;
            stack.getChildren().add(addLayer.getLayer().getNodes());
            stack.setAlignment(addLayer.getLayer().getNodes(), Pos.TOP_LEFT);
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
        if (board.hasImage()) {
            addNonBG(new Vector(event.getX(), event.getY()), board.getImage());
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
