package engine.entities;

import java.util.ArrayList;
import java.util.List;

import engine.renderer.DBRenderer.Renderer;
import engine.scripts.IScript;
import javafx.scene.image.Image;
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
	
	private int myGridSize;
	private Layer myBGLayer;
	private List<Layer> myLayerList;
	
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
		myLayerList = new ArrayList<Layer>();
	}
	
	private Entity createEnt(Vector pos) {
		Entity myEnt = new Entity(pos, SCRIPTL);
		return myEnt;
	}
	
//	/**  This should be reimplement later when the image script can set initial value so that the imagescript could be
//	 * appended when the entity was created and set to a certain size
//	 * add background block
//	 * @param name
//	 * @param pos
//	 * @return BGblock
//	 */
//	public Entity addBG(Vector pos) {
//		Entity BGblock = createEnt(pos);
//		BGblock.getRender().setScale(new Vector(myGridSize, myGridSize));
//		myBGLayer.addEntity(BGblock);
//		return BGblock;
	
	/**
	 * add static entities that are not background
	 * @param pos
	 * @param level
	 * @param size
	 * @return created entity
	 */
	public Entity addNonBG(Vector pos, int level, Vector size) {
		Entity staEnt = createEnt(pos);
		//replace this with imagescript
//		staEnt.getRender().setScale(size);
		
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
	 * get all the nonBG entities
	 * @return non-background entities
	 */
	public List<Entity> getNonBGEntity() {
		if (! myLayerList.isEmpty()) {
			List<Entity> allEnt = new ArrayList<Entity>();
			for (Layer each: myLayerList) {
				allEnt.addAll(each.getEntiy());
			}
			return allEnt;
		}
		else {
			return null;
		}
	}
	
	/**
	 * get all the BG entities
	 * @return background entities
	 */
	public List<Entity> getBGEntity() {
		return myBGLayer.getEntiy();
	}
	
	/**
	 * select any single layer, background layer is view only
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
	 * select background layer for viewing and editing
	 */
	public void selectBGLayer() {
		myBGLayer.select();
		for (Layer each: myLayerList) {
			each.deselect();
		}
	}
	
	/**
	 * show all layer in view only mode
	 */
	public void allLayer() {
		myBGLayer.onlyView();
		for (Layer each: myLayerList) {
			each.onlyView();
		}
	}
	

}
