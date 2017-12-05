package engine;

import database.filehelpers.FileDataFolders;
import database.filehelpers.FileDataManager;
import engine.camera.Camera;
import engine.entities.Entity;
import engine.events.ImageViewEvent;
import engine.events.KeyPressEvent;
import engine.events.MapEvent;
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
import java.util.Iterator;
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
        addLevel("Level 1", 5000, 5000);
        currentLevel = levels.get("level 1");
        for(String key : levels.keySet()) {
            Entity entity = levels.get(key);
            entity.getNodes().getScene().setOnKeyPressed(e -> new KeyPressEvent(e.getCode()).fire(entity));
        }
    }

    public void addBG(Vector pos) {
        if (mode == 0) {
            Entity BGblock = new Entity(currentLevel.getChildren().get(0));
            ImageView view = new ImageView();
            changeScriptBGType(view);
            setupImage(pos, view);
            BGblock.add(view);
            ImageViewEvent addView = new ImageViewEvent("setView");
            BGblock.on("setView", event -> {
                ImageViewEvent setView = (ImageViewEvent)event;
                setView.setView(view);
            });
            addView.fire(BGblock);
            BGblock.on("viewTransTrue", event -> {
                ImageViewEvent viewTrans = (ImageViewEvent)event;
                viewTrans.setMouseTransparent(true);
            });
            BGblock.on("viewTransFalse", event -> {
                ImageViewEvent viewTrans = (ImageViewEvent)event;
                viewTrans.setMouseTransparent(false);
            });
            BGblock.on("viewVisTrue", event -> {
                ImageViewEvent viewVis = (ImageViewEvent)event;
                viewVis.setVisible(true);
            });
            BGblock.on("viewVisFalse", event -> {
                ImageViewEvent viewVis = (ImageViewEvent)event;
                viewVis.setVisible(false);
            });
            view.setOnMouseClicked(e -> changeRender(e, view));
            view.setOnKeyPressed(e -> deleteEntity(e, BGblock));
        }
    }

    private void changeRender(MouseEvent event, ImageView view) {
        view.requestFocus();
        if (event.getButton().equals(MouseButton.PRIMARY) && mode == 0) {
            changeScriptBGType(view);
        }
        event.consume();
    }

    private void deleteEntity(KeyEvent event, Entity entity) {
        if (event.getCode().equals(KeyCode.BACK_SPACE)) {
            entity.getParent().remove(entity);
        }
        event.consume();
    }


    private void changeScriptBGType(ImageView view) {
        try {
            BGType.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        view.setImage(new Image(BGType));
    }



    public void addNonBG(Vector pos, InputStream image) {
        if (mode > 0) {
            if (mode > currentLevel.getChildrenSize() - 1) {
                addLayer();
            }
            Entity newEnt = new Entity(currentLevel.getChildren().get((mode)));
            try {
                image.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ImageView view = new ImageView(new Image(image));
            setupImage(pos, view);
            newEnt.add(view);
            ImageViewEvent addView = new ImageViewEvent("setView");
            newEnt.on("setView", event -> {
                ImageViewEvent setView = (ImageViewEvent)event;
                setView.setView(view);
            });
            addView.fire(newEnt);
            newEnt.on("viewTransTrue", event -> {
                ImageViewEvent viewTrans = (ImageViewEvent)event;
                viewTrans.setMouseTransparent(true);
            });
            newEnt.on("viewTransFalse", event -> {
                ImageViewEvent viewTrans = (ImageViewEvent)event;
                viewTrans.setMouseTransparent(false);
            });
            newEnt.on("viewVisTrue", event -> {
                ImageViewEvent viewVis = (ImageViewEvent)event;
                viewVis.setVisible(true);
            });
            newEnt.on("viewVisFalse", event -> {
                ImageViewEvent viewVis = (ImageViewEvent)event;
                viewVis.setVisible(false);
            });
            view.setOnMouseClicked(e -> changeRender(e, view));
            view.setOnKeyPressed(e -> deleteEntity(e, newEnt));
            view.setOnMousePressed(e -> startDrag(e, view));
            view.setOnMouseDragged(e -> drag(e, view, startPos, startSize));
        }
    }

    public void addNonBG(Vector pos, Image image) {
        if (mode > 0) {
            if (mode > currentLevel.getChildrenSize() - 1) {
                addLayer();
            }
            Entity newEnt = new Entity(currentLevel.getChildren().get(mode));
            ImageView view = new ImageView(image);
            setupImage(pos, view);
            newEnt.add(view);
            ImageViewEvent addView = new ImageViewEvent("setView");
            newEnt.on("setView", event -> {
                ImageViewEvent setView = (ImageViewEvent)event;
                setView.setView(view);
            });
            addView.fire(newEnt);
            newEnt.on("viewTransTrue", event -> {
                ImageViewEvent viewTrans = (ImageViewEvent)event;
                viewTrans.setMouseTransparent(true);
            });
            newEnt.on("viewTransFalse", event -> {
                ImageViewEvent viewTrans = (ImageViewEvent)event;
                viewTrans.setMouseTransparent(false);
            });
            newEnt.on("viewVisTrue", event -> {
                ImageViewEvent viewVis = (ImageViewEvent)event;
                viewVis.setVisible(true);
            });
            newEnt.on("viewVisFalse", event -> {
                ImageViewEvent viewVis = (ImageViewEvent)event;
                viewVis.setVisible(false);
            });
            view.setOnMouseClicked(e -> changeRender(e, view));
            view.setOnKeyPressed(e -> deleteEntity(e, newEnt));
            view.setOnMousePressed(e -> startDrag(e, view));
            view.setOnMouseDragged(e -> drag(e, view, startPos, startSize));
        }
    }

    public void selectBGLayer() {
        selectLayer(0);
    }

    public void selectLayer(int layer) {
        mode = layer;
        currentLevel.getChildren().forEach(e -> deselect(e));

        select(currentLevel.getChildren().get(layer));
    }

    public void allLayer() {
        mode = -1;
        currentLevel.getChildren().forEach(e -> viewOnly(e));
    }

    public void setBGType(InputStream image) {
        BGType = image;
    }

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
        ImageViewEvent viewTrans = new ImageViewEvent("viewTransFalse");
        ImageViewEvent viewVis = new ImageViewEvent("viewVisTrue");

        layer.getChildren().forEach(e -> {
            viewTrans.fire(e);
            viewVis.fire(e);
        });
    }

    private void deselect(Entity layer) {
        ImageViewEvent viewTrans = new ImageViewEvent("viewTransTrue");
        ImageViewEvent viewVis = new ImageViewEvent("viewVisFalse");
        layer.getChildren().forEach(e -> {
            viewTrans.fire(e);
            viewVis.fire(e);
        });
    }

    private void viewOnly(Entity layer) {
        ImageViewEvent viewTrans = new ImageViewEvent("viewTransTrue");
        ImageViewEvent viewVis = new ImageViewEvent("viewVisTrue");
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
        event.consume();
    }



    private void setupImage(Vector pos, ImageView view) {
        view.setFitWidth(grid);
        view.setFitHeight(grid);
        view.setX(FXProcessing.getXImageCoord(pos.at(0), view));
        view.setY(FXProcessing.getYImageCoord(pos.at(1), view));
    }

    private void initializeLevelMap() {
        try {
            root.getChildren().forEach(e -> levels.put((String) e.getProperty("name"), e));

        } catch(ClassCastException e) {
            ErrorDisplay eDisplay = new ErrorDisplay("Fuck you", "Name was not string");
            eDisplay.displayError();
        }
    }

    public void addLayer() {
        Entity layer = new Entity(currentLevel);
        ImageView holder = new ImageView(new Image(manager.readFileData("holder.gif")));
        holder.setX(0);
        holder.setY(0);
        holder.setFitWidth(grid);
        holder.setFitHeight(grid);
        holder.setMouseTransparent(true);
        layer.add(holder);
        MapEvent mEvent = new MapEvent("addLayer");
        currentLevel.on("addLayer", event -> {
            MapEvent addLayer = (MapEvent) event;
            addLayer.addLayer(layer);
        });
        mEvent.fire(currentLevel);
    }

    public void addLevel(String name, int mapWidth, int mapHeight) {
        Entity level = new Entity(root);
        //somehow fucking add the name to level properties
        levels.put(name, level);
        Entity BGlayer = new Entity(level);
        Canvas canvas = new Canvas(mapWidth, mapHeight);
        StackPane stack = new StackPane();
        stack.getChildren().add(canvas);
        stack.getChildren().add(BGlayer.getNodes());
        stack.setAlignment(BGlayer.getNodes(), Pos.TOP_LEFT);

        canvas.setOnMouseClicked(e -> addBGCenter(new Vector(e.getX(), e.getY()), e));
        canvas.setOnMousePressed(e -> startDragBatch(e));
        canvas.setOnMouseReleased(e -> addBatch(e, startPosBatch));

        stack.setOnDragOver(e -> dragOver(e, stack));
        stack.setOnDragDropped(e -> dragDropped(e));

        MapEvent mEvent = new MapEvent("addStack");
        level.on("addStack", event -> {
            MapEvent addStack = (MapEvent) event;
            addStack.setStack(stack);
        });
        mEvent.fire(level);
        level.add(stack);
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
            new ErrorDisplay("Fuck you!", "Level " + level + " does not exist");
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
