package engine.scripts;

import java.util.ArrayList;
import java.util.List;

import engine.entities.Entity;
import engine.managers.CollisionManager;
import engine.managers.HitBoxCheck;
import javafx.scene.Node;

/**Represents what an entity does upon collision
 *
 *@author lasia
 *@author Albert
 */
public class CollisionConditional extends Conditional {

	/**Creates a new CollisionConditional
	 *
	 * @param HitBox reference to a hitbox that is made within CollisionManager
	 * @param Scripts list of defaults to run when colliding with a particular tag
	 */
	public CollisionConditional(HitBoxCheck hitbox,List<IScript> scripts) {
		super(CollisionManager.getInstance(),hitbox,scripts);
	}

	/**Creates a new CollisionConditional
	 *
	 * @param HitBox reference to a hitbox that is made within CollisionManager
	 */
	public CollisionConditional(HitBoxCheck hitbox) {
		this(hitbox,new ArrayList<IScript>());
	}

}
