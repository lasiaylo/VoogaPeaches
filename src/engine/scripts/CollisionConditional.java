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
	 * @param cManager collision manager
	 * @param hitbox HitBox reference to a hitbox that is made within CollisionManager
	 * @param scripts list of defaults to run when colliding with a particular tag
	 */
	public CollisionConditional(CollisionManager cManager, HitBoxCheck hitbox,List<IScript> scripts) {
		super(cManager,hitbox,scripts);
	}

	/**Creates a new CollisionConditional
	 *
	 * @param hitbox reference to a hitbox that is made within CollisionManager
	 */
	public CollisionConditional(CollisionManager cManager, HitBoxCheck hitbox) {
		this(cManager, hitbox,new ArrayList<IScript>());
	}

}
