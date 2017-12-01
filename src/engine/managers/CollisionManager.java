package engine.managers;


import com.google.gson.annotations.Expose;
import database.firebase.TrackableObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that tells Entities what other Entities they have collided with
 * @author Albert
 * @author lasia
 */
public class CollisionManager extends TrackableObject implements IManager {
	@Expose private List<HitBox> myHitBoxes;

	/**
	 * Creates a new CollisionManager
	 */
	public CollisionManager() {
		myHitBoxes = new ArrayList<>();
	}

	/**Checks whether this Hitbox is colliding with other Hitboxes
	 * @param hitBox Hitbox
	 */
	public void checkCollisions(HitBox hitBox) {
		for (HitBox otherHitBox: myHitBoxes) {
			if (!otherHitBox.equals(hitBox))
				hitBox.checkIntersect(otherHitBox);
		}
	}

	/**
	 * Adds a hit box to the collection
	 * @param hitBox	hitbox to be added
	 */
	public void addHitBox(HitBox hitBox) {
		myHitBoxes.add(hitBox);
	}

	@Override
	public boolean check(Object object) {
		HitBoxCheck checker = (HitBoxCheck) object;
		HitBox hitBox = checker.getHitBox();
		String tagToFind = checker.getTag();
		checkCollisions(hitBox);
		List<String> visitorTag = hitBox.getVisitors();
		return visitorTag.contains(tagToFind);
	}
}
