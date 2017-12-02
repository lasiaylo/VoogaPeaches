package engine.managers;

import com.google.gson.annotations.Expose;
import database.firebase.TrackableObject;

/**Wrapper class for hitbox and a tag
 * To be passed into CollisionManager's check
 * The tag is used to see if a particular hitbox has this particular tag
 * @author lasia
 *
 */
public class HitBoxCheck extends TrackableObject {
	@Expose private HitBox myHitBox;
	@Expose private String myTag;

	/**
	 * Creates a new HitBoxCheck from the database
	 */
	public HitBoxCheck() {}

	public HitBoxCheck(HitBox hitbox, String tag) {
		myHitBox = hitbox;
		myTag = tag;
	}
	
	public HitBox getHitBox() {
		return myHitBox;
	}
	
	public void setHitBox(HitBox myHitBox) {
		this.myHitBox = myHitBox;
	}
	
	public String getTag() {
		return myTag;
	}
	
	public void setTag(String myTag) {
		this.myTag = myTag;
	}
}
