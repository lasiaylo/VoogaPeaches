package engine;

import database.filehelpers.FileDataFolders;
import database.filehelpers.FileDataManager;
import engine.camera.Camera;
import engine.entities.Entity;
import engine.events.ImgViewEvent;
import engine.events.KeyPressEvent;
import engine.util.FXProcessing;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
    private Camera camera;
    private int mode = -1;
    private InputStream BGType;
    private int grid;
    private FileDataManager manager;
    private Vector startPos = new Vector(0, 0);
    private Vector startSize = new Vector(0, 0);


    public EntityManager(Entity root, String currentLevel, Camera camera, int gridSize) {
        this.root = root;
        this.levels = new HashMap<>();
        this.camera = camera;
        this.grid = gridSize;

        manager = new FileDataManager(FileDataFolders.IMAGES);
        BGType = manager.readFileData("Background/grass.png");

        initializeLevelMap();
        for(String key : levels.keySet()) {
            Entity entity = levels.get(key);
            entity.getNodes().getScene().setOnKeyPressed(e -> new KeyPressEvent(e.getCode()).fire(entity));
        }
    }

    public void addBG(Vector pos) {
        if (mode == 0) {
            Entity BGblock = new Entity(currentLevel.getChildren(0));
            ImageView view = new ImageView();
            changeScriptBGType(view);
            setupImage(pos, view);
            BGblock.add(view);
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
            Entity newEnt = new Entity(currentLevel.getChildren(mode));
            try {
                image.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ImageView view = new ImageView(new Image(image));
            setupImage(pos, view);
            newEnt.add(view);
            view.setOnMouseClicked(e -> changeRender(e, view));
            view.setOnKeyPressed(e -> deleteEntity(e, newEnt));
            view.setOnMousePressed(e -> startDrag(e, view));
            view.setOnMouseDragged(e -> drag(e, view, startPos, startSize));
        }
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
            Iterator<Entity> children = root.getChildren();
            while(children.hasNext()) {
                Entity child = children.next();
                levels.put((String) child.getProperty("name"), child);
            }
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
    }

    public void addLevel(String name) {
        Entity level = new Entity(root);
        //somehow fucking add the name to level properties
        levels.put(name, level);
        Entity BGlayer = new Entity(level);
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
            camera.setView((currentLevel = this.levels.get(level)));
//            camera = new Camera((currentLevel = this.levels.get(level)).getNodes());
    }



    public Entity getCurrentLevel() {
        return currentLevel;
    }

    public Entity getRoot() {
        return root;
    }
}
