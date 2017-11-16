package engine.managers;

/**Wrapper class for hitbox and a tag
 * To be passed into CollisionManager's check
 * The tag is used to see if a particular hitbox has this particular tag
 * @author lasia
 *
 */
public class HitBoxCheck {
	private HitBox myHitBox;
	private String myTag;
	
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
