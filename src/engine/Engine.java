package engine;

import engine.camera.Camera;
import engine.camera.Map;
import engine.entities.EngineLoop;
import engine.entities.Entity;
import engine.managers.EntityManager;
import javafx.animation.Timeline;
import javafx.scene.SubScene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import util.math.num.Vector;

import java.awt.*;
import java.util.List;

/**
 * Class that holds the entities together
 * @author ramilmsh
 * @author Albert
 */
public class Engine {
    private List<Entity> myEntities;
    private EntityManager myManager;
    private EngineLoop myGameLoop;
    private Timeline myTimeline;
    private Camera myCamera;
    
    public Engine(Number gridSize, Number mapWidth, Number mapHeight) {
    		myManager = new EntityManager(gridSize.intValue());
    		myGameLoop = new EngineLoop(myManager, myCamera);
    		myTimeline = myGameLoop.getTimeline();
    		myCamera = new Camera(new Map(myManager, gridSize.intValue(), mapWidth.intValue(), mapHeight.intValue()));
    }

    /**
     * Finds the sector in which the param resides
     * @param entity    entity to find sector for
     * @return          List of objects in the entity's sector
     */
    public List<Entity> getSector(Entity entity) {
        // TO DO
        return null;
    }
    
    /**
     * start or resume the game loop
     */
    public void play() {
    		myTimeline.play();
    }
    
    /**
     * pause the game loop
     */
    public void pause() {
    		myTimeline.pause();
    }
    
    /**
     * get entity manager
     * @return EntityManager
     */
    public EntityManager getEntityManager() {
    		return myManager;
    }

    /**
     * get camera view for camerapane
     * @param center
     * @param size
     * @return
     */
    public ScrollPane getCameraView(Vector center, Vector size) {
        return myCamera.getView(center, size);
    }


    /**
     * get minimap
     * @param size
     * @return minimap
     */
    public Pane getMiniMap(Vector size) {
        return myCamera.getMiniMap(size);
    }

}
