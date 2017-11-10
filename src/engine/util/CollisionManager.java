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
 */
public class CollisionManager {
    private Engine myEngine;
    // Singleton??
    private static CollisionManager instance;
    private  List<HitBox> myHitBoxes;
    
    /**
     * Creates a new CollisionManager
     * @param engine    Engine holding everything together (needed for object list)
     */
    public CollisionManager() {
    }

    public static void checkCollision(HitBox hitbox) {
    	for (HitBox hitBox : myHitBoxes) {
    		
    	}
    }

    
    
    
    /**
     * @param engine	Engine to be passed in
     * @return			Singleton instance of CollisionManager
     */
    public static CollisionManager getInstance(Engine engine) {
    	if (instance == null) {
    		instance = new CollisionManager(engine);
    	}
    	
    	return instance;
    }
}
