package engine.scripts;

import java.util.Map;
import java.util.Set;

import engine.entities.Entity;
import javafx.scene.Node;

/**Define the qualities of an Entity
 * 
 * @author lasia
 * @author Albert
 */
public interface IScript {
	
    /**Runs the Script
     * @param entity
     */
    void execute(Entity entity);
}
