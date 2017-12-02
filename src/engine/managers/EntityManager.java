package engine.managers;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import database.filehelpers.FileDataManager;
import database.firebase.TrackableObject;
import engine.entities.Entity;
import engine.entities.Layer;
import engine.scripts.defaults.ImageScript;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
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
	private int myMode = -1;

	/**
	 * manage all entities and layers
	 *
	 * middleman between authoring and backend control of entities
	 *
	 * @param gridSize
	 */
	public EntityManager(Number gridSize) {
		myGridSize = gridSize.intValue();
		myBGLayer = new Layer(0);
		myLayerList = FXCollections.observableList(new ArrayList<Layer>());
        FileDataManager manager = new FileDataManager(FileDataManager.FileDataFolders.IMAGES);
        myBGType = manager.readFileData("Background/grass.png");
	}


	/**  This should be reimplement later when the image script can set initial value so that the imagescript could be
	 * appended when the entity was created and set to a certain size
	 * add background block
	 * @param pos
	 * @return BGblock
	 */
	public Entity addBG(Vector pos) {
	    if (myMode == 0) {
            Entity BGblock = myBGLayer.addEntity(pos);
            ImageScript script = new ImageScript();
            changeScriptBGType(script, BGblock);
            BGblock.addScript(script);
            BGblock.getRender().setOnMouseClicked(e -> changeRender(e.getButton(), script, BGblock));
            return BGblock;
        }
        return null;
	}



	private void deleteEntity(Entity ent) {
        if (myMode == 0) {
            myBGLayer.deleteEntity(ent);
        }
        else if (myMode > 0){
            myLayerList.get(myMode - 1).deleteEntity(ent);
        }
    }

    private void changeRender(MouseButton mouse, ImageScript scripts, Entity entity) {
	    if (mouse.equals(MouseButton.PRIMARY) && myMode == 0) {
	        changeScriptBGType(scripts, entity);
        }
        else if(mouse.equals(MouseButton.SECONDARY)) {
	        deleteEntity(entity);
        }
    }

	private void changeScriptBGType(ImageScript script, Entity entity) {
        try {
            myBGType.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        script.setFilename(myBGType);
        script.execute(entity);
    }

	/**
	 * add static entities that are not background
	 * @param pos
	 * @return created entity
	 */
	public Entity addNonBG(Vector pos, InputStream image) {
	    if (myMode > 0) {
	        if (myMode > myLayerList.size()) {
	            addLayer();
            }
            Entity newEnt = myLayerList.get(myMode - 1).addEntity(pos);
            ImageScript script = new ImageScript();
            try {
                image.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
            script.setFilename(image);
            newEnt.addScript(script);
            script.execute(newEnt);
            newEnt.getRender().setOnMouseClicked(e -> changeRender(e.getButton(), script, newEnt));

            return newEnt;
        }
        return null;
	}

	/**
	 * add new layer
	 * @return new layer
	 */
	public Layer addLayer() {
		Layer current = new Layer(myLayerList.size() + 1);
		myLayerList.add(current);
		return current;
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
		myMode = level;
		level -= 1;
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
		myMode = 0;
	}

	/**
	 * show all layer in view only mode, so basically player mode
	 */
	public void allLayer() {
		myBGLayer.onlyView();
		for (Layer each: myLayerList) {
			each.onlyView();
		}
		myMode = -1;
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




}
