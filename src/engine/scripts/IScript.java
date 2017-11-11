package engine.scripts;

import engine.entities.Entity;
import javafx.scene.Node;

/**Define the qualities of an Entity
 * 
 * @author lasia
 * @author Albert
 */
public interface IScript {
    void execute(Entity entity);
}
