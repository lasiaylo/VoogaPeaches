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
	private CollisionManager myCollisionManager;
	
	public CollisionConditional(HitBox hitbox) {
		myHitBox = hitbox;
		myCollisionManager = CollisionManager.getInstance();
	}
	
	public void setTag(String newTag) {
		conditionTag = newTag;
	}
	@Override
	public void execute(Entity entity) {
		myCollisionManager.checkCollisions(myHitBox);
		String visitorTag = myHitBox.getVisitor();
		if (visitorTag.equals(conditionTag)) {
			for(IScript script : myScripts)
				
		myHitBox.setPosition(Entity.pos);
	}

}
