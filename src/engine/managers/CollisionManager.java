package engine.managers;


import java.util.List;

/**
 * A class that tells Entities what other Entities they have collided with
 * @author Albert
 * @authoer lasia
 */
public class CollisionManager implements IManager {
    // Singleton??
    private static CollisionManager instance;
    private List<HitBox> myHitBoxes;
    
    /**
     * Creates a new CollisionManager
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
     * @return			Singleton instance of CollisionManager
     */
    public static CollisionManager getInstance() {
    	if (instance == null)
    		instance = new CollisionManager();
    	
    	return instance;
    }

	@Override
	public boolean check(Object object) {
		HitBoxCheck checker = (HitBoxCheck) object;
		HitBox hitBox = checker.getHitBox();
		String tag = checker.getTag();
		
		checkCollisions(hitBox);
		List<String> visitorTag = hitBox.getVisitors();
		return visitorTag.contains(tag);
	}
}
