package engine.scripts;

import java.util.List;

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
	private Entity myEntity;
	
	public CollisionConditional(Entity entity, HitBox hitbox) {
		myHitBox = hitbox;
		myCollisionManager = CollisionManager.getInstance();
		myEntity = entity;
	}
	
	public void setTag(String newTag) {
		conditionTag = newTag;
	}
	@Override
	public void execute(Entity entity) {
		myCollisionManager.checkCollisions(myHitBox);
		List<String> visitorTag = myHitBox.getVisitor();
		
		if (visitorTag.contains(conditionTag)) {
			for(IScript script : getScripts()) {
				myHitBox.setPosition(myEntity.getPosition());
			}
		}
	}	
}
