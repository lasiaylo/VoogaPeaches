package engine.entities;

import engine.scripts.IScript;
import javafx.scene.Node;
import util.math.num.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Base engine class that is used as a template for all objects in game.
 *
 * @author Albert
 *
 */
public class Entity {
    private Vector myPosition;
    private Node mySprite;
    private boolean isStatic;
    private List<IScript> myScripts;

    /**
     *  Create a new Entity
     * @param pos       Vector position of new Entity
     * @param scripts   Scripts attached to new Entity
     */
    public Entity(Vector pos, List<IScript> scripts) {
        myPosition = pos;
        myScripts = scripts;
    }

    /**
     * Create a new Entity
     * @param x         X position of new Entity
     * @param y         Y position of new Entity
     * @param scripts   Scripts attached to new Entity
     */
    public Entity(double x, double y, List<Iscript> scripts) {
        this(new Vector(x, y), scripts);
    }

    /**
     * @return  Vector position of this entity
     */
    public Vector getPosition() {
        return myPosition;
    }
    
    /**
     * @param New position for this entity
     */
    public void setPosition(Vector newPos) {
    	myPosition = newPos;
    }

    /**
     * Run all scripts attached to the Entity
     */
    public void update() {
        for(IScript s : myScripts) {
            s.execute(this);
        }
    }

    /**
     * @return  List of entity's scripts
     */
    public List<IScript> getScripts(){
    	return myScripts;
    }
    
    /**
     * @return Whether the entity is static or not. If an entity is static, it just needs to be updated once.
     */
    public boolean isStatic() {
    	return isStatic;
    }
    
}
