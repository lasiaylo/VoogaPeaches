package engine.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Store entities in each layer of the gaming world
 * @author estellehe
 *
 */
public class Layer {
	private List<Entity> myEntityList;
	
	public Layer() {
		myEntityList = new ArrayList<Entity>();
	}
	
	/**
	 * add entity to layer
	 * @param each
	 */
	public void addEntity(Entity each) {
		myEntityList.add(each);
	}
	
	/**
	 * get entity list from the layer
	 * @return
	 */
	public List<Entity> getEntiy() {
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

}
