package engine.managers;


import java.util.ArrayList;
import java.util.List;

import engine.entities.Entity;
import engine.entities.Layer;
import engine.scripts.IScript;
import engine.scripts.Script;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import util.exceptions.GroovyInstantiationException;
import util.math.num.Vector;

/**
 * create and hold all entities displayed
 * 
 * should be accessible to authoring for adding entities
 * 
 * @author estellehe
 *
 */
public class EntityManager {
	private static final List<IScript> SCRIPTL = new ArrayList<IScript>();
	private static final String IMGSPT = "ImageScript.groovy";
	
	private int myGridSize;
	private Layer myBGLayer;
	private ObservableList<Layer> myLayerList;
	
	/**
	 * manage all entities and layers
	 * 
	 * middleman between authoring and backend control of entities
	 * 
	 * @param gridSize
	 */
	public EntityManager(Number gridSize) {
		myGridSize = gridSize.intValue();
		myBGLayer = new Layer();
		myLayerList = FXCollections.observableList(new ArrayList<Layer>());
	}
	
	private Entity createEnt(Vector pos) {
		Entity myEnt = new Entity(pos, SCRIPTL);
		return myEnt;
	}
	
	/**  This should be reimplement later when the image script can set initial value so that the imagescript could be
	 * appended when the entity was created and set to a certain size
	 * add background block
	 * @param pos
	 * @return BGblock
	 */
	public Entity addBG(Vector pos) {
        Entity BGblock = createEnt(pos);
        try {
            BGblock.addSript(new Script(IMGSPT));
            //todo: add gridsize to image script
            BGblock.update();
            System.out.println(BGblock.getRender().getImage().getX());
            System.out.println(BGblock.getRender().getImage().getY());
        }
        catch (GroovyInstantiationException e) {
            //todo: error msg

        }
        myBGLayer.addEntity(BGblock);
        return BGblock;
    }
	
	/**
	 * add static entities that are not background
	 * @param pos
	 * @param level
	 * @param size
	 * @return created entity
	 */
	public Entity addNonBG(Vector pos, int level, Vector size) {
		Entity staEnt = createEnt(pos);
        try {
            staEnt.addSript(new Script(IMGSPT));
            staEnt.update();
        }
        catch (GroovyInstantiationException e) {
            //todo: error msg here
        }
		
		if (level > myLayerList.size()-1) {
			Layer myLayer = new Layer();
			myLayer.addEntity(staEnt);
			myLayerList.add(myLayer);
		}
		else {
			myLayerList.get(level).addEntity(staEnt);
		}
		return staEnt;
		
	}
	
	/**
	 * add nonstatic entities
	 * @param pos
	 * @param level
	 * @param size
	 * @return Entity
	 */
	public Entity addNonStatic(Vector pos, int level, Vector size) {
		Entity Ent = addNonBG(pos, level, size);
		Ent.setStatic(false);
		return Ent;
	}

    /**
     * get group of background imageview
     * @return BG imageview group
     */
	public Group getBGImageList() {
	    return myBGLayer.getImageList();
    }


    /**
     * add listener for layerlist
     * @param listener
     */
    public void addLayerListener(ListChangeListener listener) {
        myLayerList.addListener(listener);
    }

	/**
	 * select any single layer, background layer is view only, for authoring mode
	 * @param level
	 */
	public void selectLayer(int level) {
		myBGLayer.onlyView();
		for (Layer each: myLayerList) {
			each.deselect();
		}
		myLayerList.get(level).select();
	}
	
	/**
	 * select background layer for viewing and editing, for authoring mode
	 */
	public void selectBGLayer() {
		myBGLayer.select();
		for (Layer each: myLayerList) {
			each.deselect();
		}
	}
	
	/**
	 * show all layer in view only mode, so basically player mode
	 */
	public void allLayer() {
		myBGLayer.onlyView();
		for (Layer each: myLayerList) {
			each.onlyView();
		}
	}

    /**
     * update all entities in every layer
     */
	public void updateAll() {
	    myBGLayer.updateAll();
	    for (Layer each: myLayerList) {
	        each.updateAll();
        }
    }

    /**
     * update imageview of entities inside box
     * @param center
     * @param size
     */
    public void displayUpdate(Vector center, Vector size) {
        myBGLayer.displayUpdate(center, size);
        for (Layer each: myLayerList) {
            each.displayUpdate(center, size);
        }
    }

}
