package engine.entities;

import engine.scripts.Script;
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
    private List<Script> myScripts;

    /**
     *  Create a new Entity
     * @param pos       Vector position of new Entity
     * @param scripts   Scripts attached to new Entity
     */
    public Entity(Vector pos, List<Script> scripts) {
        myPosition = pos;
        myScripts = scripts;
    }

    /**
     * Create a new Entity
     * @param x         X position of new Entity
     * @param y         Y position of new Entity
     * @param scripts   Scripts attached to new Entity
     */
    public Entity(double x, double y, List<Script> scripts) {
        this(new Vector(x, y), scripts);
    }

    /**
     * @return  the vector position of this entity
     */
    public Vector getPosition() {
        return myPosition;
    }

    /**
     * run all scripts attached to the Entity
     */
    public void run() {
        for(Script s : myScripts) {
            s.execute(this);
        }
    }

    /**
     * @return  list of javafx nodes corresponding to this entity's scripts
     */
    public List<Node> getScriptDisplays() {
        List<Node> scriptDisplays = new ArrayList<>();
        for(Script s : myScripts) {
            scriptDisplays.add(s.getNode());
        }

        return scriptDisplays;
    }

    /**
     * add a new script to the Entity
     * @param s script to be added
     */
    public void add(Script s) {
        myScripts.add(s);
    }
}
