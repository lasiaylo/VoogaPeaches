package engine.entities;

import engine.scripts.Script;
import engine.util.FXProcessing;
import engine.scripts.IScript;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import util.math.num.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Base engine class that is used as a template for all objects in game.
 *
 * @author Albert
 * @author lasia
 * @author estellehe
 *
 */
public class Entity {
	private Transform myTransform;
	private Render myRender;
    private boolean isStatic;
    private List<IScript> myScripts;

    /**
     *  Creates a new Entity
     *  @param pos       Vector position of new Entity
     *  @param scripts   Scripts attached to new Entity
     */
    public Entity(Vector pos, List<IScript> scripts) {
    	myTransform = new Transform(pos);
        myScripts = scripts;
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
