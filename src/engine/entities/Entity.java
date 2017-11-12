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
 *
 */
public class Entity {
    private Vector myPosition;
    private Vector myVelocity;
    private int myID;
    private ImageView myImageView;
    private boolean isStatic;
    private List<IScript> myScripts;

    /**
     *  Creates a new Entity
     *  @param id        database id of entity
     *  @param image     Image attached to Entity
     *  @param pos       Vector position of new Entity
     *  @param scripts   Scripts attached to new Entity
     */
    public Entity(Number id, Image image, Vector pos, List<IScript> scripts) {
        myPosition = pos;
        myScripts = scripts;
        myID = (int) id;

        myImageView = new ImageView(image);
        displayUpdate();
    }

    /**
     * Create a new Entity
     * @param id        database id of entity
     * @param image     Image attached to Entity
     * @param x         X position of new Entity
     * @param y         Y position of new Entity
     * @param scripts   Scripts attached to new Entity
     */
    public Entity(Number id, Image image, List<IScript> scripts, double x, double y) {
        this(id,image, new Vector(x, y), scripts);
    }

    /**
     * @return  Vector position of this entity
     */
    public Vector getPosition() {
        return myPosition;
    }

    /**
     * @param newPos position for this entity
     */
    public void setPosition(Vector newPos) {
    	myPosition = newPos;
    }

	/**
	 * run all scripts attached to the Entity
	 */
	public void update() {
		for (IScript s : myScripts) {
			s.execute(this);
		}
	}

	/**
	 * @return List of entity's scripts
	 */
	public List<IScript> getScripts() {
		return myScripts;
	}
	
	/**
	 * apply the position of entity to its imageview
	 */
	public void displayUpdate() {
		myImageView.setX(FXProcessing.getXImageCoord(myPosition.at(0), myImageView));
        myImageView.setY(FXProcessing.getYImageCoord(myPosition.at(1), myImageView));
	}

	/**
	 * @return Whether the entity is static or not. If an entity is static, it just
	 *         needs to be updated once.
	 */
	public boolean isStatic() {
		return isStatic;
	}
	
	/**
	 * change the size (width, height) of the imageview
	 * @param size
	 */
	public void resize(Vector size) {
		myImageView.setFitWidth(size.at(0));
		myImageView.setFitHeight(size.at(1));
	}
	
	/**
	 * set entity movable
	 */
	public void setMovable() {
		isStatic = false;
	}

}
