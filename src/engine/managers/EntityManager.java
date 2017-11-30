package engine.managers;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import database.filehelpers.FileDataManager;
import database.firebase.TrackableObject;
import engine.entities.Entity;
import engine.entities.Layer;
import engine.scripts.Script;
import engine.scripts.defaults.ImageScript;
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
public class EntityManager extends TrackableObject {
	private static final String IMGSPT = "defaults/ImageScript.groovy";

	private int myGridSize;
	private Layer myBGLayer;
	private ObservableList<Layer> myLayerList;
	private InputStream myBGType;
	private int myLevel = 1;
	private String myMode = "All";

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
        FileDataManager manager = new FileDataManager(FileDataManager.FileDataFolders.IMAGES);
        myBGType = manager.readFileData("Background/grass.png");
	}

	private Entity createEnt(Vector pos) {
		Entity myEnt = new Entity(pos);
		return myEnt;
	}

	/**  This should be reimplement later when the image script can set initial value so that the imagescript could be
	 * appended when the entity was created and set to a certain size
	 * add background block
	 * @param pos
	 * @return BGblock
	 */
	public Entity addBG(Vector pos) {
	    if (myMode.equals("BG")) {
            Entity BGblock = createEnt(pos);
            ImageScript script = new ImageScript();
            try {
                myBGType.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
            script.setFilename(myBGType);
            BGblock.addScript(script);
            BGblock.update();
            myBGLayer.addEntity(BGblock);
            return BGblock;
        }
        return null;
	}

	/**
	 * add static entities that are not background
	 * @param pos
	 * @return created entity
	 */
	public Entity addNonBG(Vector pos, InputStream image) {
	    if ((!myMode.equals("BG")) && (!myMode.equals("All"))) {
            Entity staEnt = createEnt(pos);
            ImageScript script = new ImageScript();
            try {
                image.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
            script.setFilename(image);
            staEnt.addScript(script);
            staEnt.update();

            if (myLevel > myLayerList.size()-1) {
                Layer myLayer = addLayer();
                myLayer.addEntity(staEnt);
            }
            else {
                myLayerList.get(myLevel).addEntity(staEnt);
            }
            return staEnt;
        }
        return null;
	}

	/**
	 * add new layer
	 * @return new layer
	 */
	public Layer addLayer() {
		Layer current = new Layer();
		myLayerList.add(current);
		return current;
	}

	/**
	 * add nonstatic entities
	 * @param pos
	 * @return Entity
	 */
	public Entity addNonStatic(Vector pos, InputStream image) {
		Entity Ent = addNonBG(pos, image);
		if (Ent != null) {
            Ent.setStatic(false);
        }
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
		// potential issue doesn't add to background?
	}

	/**
	 * select any single layer, background layer is view only, for authoring mode
	 * @param level
	 */
	public void selectLayer(int level) {
		level -= 1;
		myBGLayer.onlyView();
		for (Layer each: myLayerList) {
			each.deselect();
		}
		myLayerList.get(level).select();
		myMode = "" + level;
	}

	/**
	 * select background layer for viewing and editing, for authoring mode
	 */
	public void selectBGLayer() {
		myBGLayer.select();
		for (Layer each: myLayerList) {
			each.deselect();
		}
		myMode = "BG";
	}

	/**
	 * show all layer in view only mode, so basically player mode
	 */
	public void allLayer() {
		myBGLayer.onlyView();
		for (Layer each: myLayerList) {
			each.onlyView();
		}
		myMode = "All";
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

    /**
     * change background type for clicking
     * @param type
     */
	public void setMyBGType (InputStream type) {
	    myBGType = type;
    }


    /**
     * change current editing layer
     * @param level
     */
    public void setMyLevel (int level) {
	    myLevel = level - 1;
    }

    /**
     * get current mode
     * @return myMode
     */
    public String getMyMode() {
        return myMode;
    }

}
