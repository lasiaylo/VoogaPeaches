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
	
	public void addEntity(Entity each) {
		myEntityList.add(each);
	}
	
	public List<Entity> getEntiy() {
		return myEntityList;
	}

}
