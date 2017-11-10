package engine.scripts;

import engine.entities.Entity;
import engine.util.CollisionManager;
import javafx.scene.Node;

/**Represents what an entity does upon collision
 * 
 *@author lasia
 *@author Albert
 */
public class CollisionConditional extends Conditional {
	private HitBox myHitBox;
	private String conditionTag;
	
	public CollisionConditional(HitBox hitbox) {
		myHitBox = hitbox;
	}
	
	public void setTag(String newTag) {
		conditionTag = newTag;
	}
	@Override
	public void execute(Entity entity) {
		CollisionManager cManager = CollisionManager.getInstance();
		CollisionManager.checkCollisions(myHitBox);
		CollisionManager.getVistorTag == conditionTag
				run all the scripts
				
		myHitBox.setPosition(Entity.pos);
	}

}
