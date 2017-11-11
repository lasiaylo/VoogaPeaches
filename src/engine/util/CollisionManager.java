package engine.util;

import engine.Engine;
import engine.entities.Entity;
import engine.scripts.HitBox;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A class that tells Entities what other Entities they have collided with
 * @author Albert
 * @authoer lasia
 */
public class CollisionManager {
    private Engine myEngine;
    // Singleton??
    private static CollisionManager instance;
    private List<HitBox> myHitBoxes;
    
    /**
     * Creates a new CollisionManager
     * @param engine    Engine holding everything together (needed for object list)
     */
    public CollisionManager() {
    }

    /**Checks whether this Hitbox is colliding with other Hitboxes
     * @param Hitbox
     */
    public void checkCollisions(HitBox hitBox) {
    	for (HitBox otherHitBox: myHitBoxes) {
    		if (!otherHitBox.equals(hitBox)) 
    			hitBox.checkIntersect(otherHitBox);
    	}
    }
    
    /**
     * @param engine	Engine to be passed in
     * @return			Singleton instance of CollisionManager
     */
    public static CollisionManager getInstance() {
    	if (instance == null) {
    		instance = new CollisionManager();
    	}
    	
    	return instance;
    }
}
