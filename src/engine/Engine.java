package engine;

import engine.entities.EngineLoop;
import engine.entities.Entity;
import engine.entities.EntityManager;
import javafx.animation.Timeline;

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
    
    public Engine(Number gridSize) {
    		myManager = new EntityManager(gridSize);
    		myGameLoop = new EngineLoop(myManager);
    		myTimeline = myGameLoop.getTimeline();
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
}
