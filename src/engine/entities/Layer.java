package engine.entities;

import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * Store entities in each layer of the gaming world
 * @author estellehe
 *
 */
public class Layer {
	private List<Entity> myEntityList;
	private Group myImageList;
	
	public Layer() {

	    myEntityList = new ArrayList<Entity>();
	    myImageList = new Group();
	}
	
	/**
	 * add entity to layer
     *
     * this entity should already has an imageview inside its render
     *
     * which means adding imagescript and updating entity
	 * @param each
	 */
	public void addEntity(Entity each) {

	    myEntityList.add(each);
	    myImageList.getChildren().add(each.getRender().getImage());
	}
	
	/**
	 * get entity list from the layer
	 * @return
	 */
	public List<Entity> getEntityList() {

	    return myEntityList;
	}
	
	/**
	 * select this layer: make all entity on this layer visible and mouse-clickable
	 */
	public void select() {
		for (Entity each: myEntityList) {
			each.getRender().setMouseTrans(false);
			each.getRender().setVisible(true);
		}
	}
	
	/**
	 * deselect this layer: make all entity on this layer invisible and mouse-unclickable
	 */
	public void deselect() {
		for (Entity each: myEntityList) {
			each.getRender().setMouseTrans(true);
			each.getRender().setVisible(false);
		}
	}
	
	/**
	 * view only mode for this layer
	 */
	public void onlyView() {
		for (Entity each: myEntityList) {
			each.getRender().setMouseTrans(true);
			each.getRender().setVisible(true);
		}
	}

    /**
     * get group of imageview representation of entities
     * @return myimagelist
     */
	public Group getImageList() {
	    return myImageList;
    }

    /**
     * update all entities in the layer
     */
    public void updateAll() {
	    for (Entity each: myEntityList) {
	        each.update();
        }
    }

}
