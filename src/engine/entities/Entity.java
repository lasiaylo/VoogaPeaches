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
    private Vector myAcceleration;
    private ImageView myImageView;
    private boolean isStatic;
    private List<IScript> myScripts;

    /**
     *  Creates a new Entity
     * @param pos       Vector position of new Entity
     * @param scripts   Scripts attached to new Entity
     */
    public Entity(Vector pos, List<IScript> scripts, Image image) {
        myPosition = pos;
        myScripts = scripts;

        myImageView = new ImageView(image);
        myImageView.setX(FXProcessing.getXImageCoord(pos.at(0), myImageView));
        myImageView.setY(FXProcessing.getYImageCoord(pos.at(1), myImageView));
    }

    /**
     * Create a new Entity
     * @param x         X position of new Entity
     * @param y         Y position of new Entity
     * @param scripts   Scripts attached to new Entity
     */
    public Entity(double x, double y, List<IScript> scripts, Image image) {
        this(new Vector(x, y), scripts, image);
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
     * run all scripts attached to the Entity
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
