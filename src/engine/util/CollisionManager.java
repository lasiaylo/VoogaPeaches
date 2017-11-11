package engine.util;

import engine.Engine;
import engine.entities.Entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A class that tells Entities what other Entities they have collided with
 * @author Albert
 * @authoer lasia
 */
public class CollisionManager implements IManager{
    private Engine myEngine;
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

	@Override
	public boolean check(Object arg1, String tag) {
		HitBox hitbox = (HitBox) arg1;
		checkCollisions(hitbox);
		List<String> visitorTag = hitbox.getVisitor();
		return visitorTag.contains(tag);
	}
}
