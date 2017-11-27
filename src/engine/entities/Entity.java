package engine.entities;

import com.google.gson.annotations.Expose;
import database.firebase.TrackableObject;
import engine.scripts.IScript;
import util.math.num.Vector;

import java.util.List;

/**
 * Base engine class that is used as a template for all objects in game.
 *
 * @author Albert
 * @author lasia
 * @author estellehe
 *
 */
public class Entity extends TrackableObject {
	@Expose private Transform myTransform;
	@Expose private Render myRender;
    @Expose private boolean isStatic;
    @Expose private List<IScript> myScripts;

	/**
	 * privately creates an entity through the database
	 */
	private Entity() {

	}

    /**
     *  Creates a new Entity
     *  @param pos       Vector position of new Entity
     *  @param scripts   Scripts attached to new Entity
     */
    public Entity(Vector pos, List<IScript> scripts) {
    	super();
    	myTransform = new Transform(pos);
        myScripts = scripts;
        myRender = new Render();
    }

    /**
     * Create a new Entity
     * @param x         X position of new Entity
     * @param y         Y position of new Entity
     * @param scripts   Scripts attached to new Entity
     */
    public Entity(List<IScript> scripts, double x, double y) {
    	this(new Vector(x, y), scripts);
    }

	/**
	 * run all defaults attached to the Entity
	 */
	public void update() {
		for (IScript s : myScripts) {
			s.execute(this);
		}
	}

    /**
     * transform class that contains transform recorded for this entity
     * @return transform
     */
	public Transform getTransform() {
	    return myTransform;
	}

	/**
	 * @return Render wrapper class that contains ImageView
	 */
	public Render getRender() {
	    return myRender;
	}

    /**
     * add script to entity
     * @param script
     */
	public void addSript(IScript script) {
	    myScripts.add(script);
    }

	/**
	 * @return List of entity's defaults
	 */
	public List<IScript> getScripts() {
	    return myScripts;
	}

	/**
	 * @return Whether the entity is static or not. If an entity is static, it just
	 *         needs to be updated once.
	 */
	public boolean isStatic() {
	    return isStatic;
	}

	/**	Sets whether an entity is static or not. If an entity is static, it just needs
	 * 	to be updated once.
	 *
	 */
	public void setStatic(boolean isStatic) {
	    this.isStatic = isStatic;
	}
}

