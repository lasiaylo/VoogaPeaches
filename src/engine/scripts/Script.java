package engine.scripts;

import engine.entities.Entity;
import javafx.scene.input.KeyCode;

/**
 *
 * @author Albert
 */
public abstract class Script implements IScript {
    private Entity myEntity;

    public Script(Entity entity) {
        myEntity = entity;
    }

    protected Entity getEntity() {
        return myEntity;
    }

    public abstract void keyInput(KeyCode code);
}
