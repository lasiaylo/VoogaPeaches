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
	private static final String DBNAME = "Some database name idk";
	private static final List<IScript> SCRIPTL = new ArrayList<IScript>();
	
	private int myGridSize;
	private Layer myBGLayer;
	private Renderer myRenderer;
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
		myRenderer = new Renderer(DBNAME); //probably not the right way to render, have to figure out later
		myLayerList = new ArrayList<Layer>();
	}
	
	private Entity createEnt(String name, Vector pos) {
//		Image someimage = new Image("resources/graphics/sprite_test.png");  //need to get from the renderer
		Entity myEnt = new Entity(pos, SCRIPTL);
		return myEnt;
	}
	
	/**
	 * add background block
	 * @param name
	 * @param pos
	 * @return BGblock
	 */
	public Entity addBG(String name, Vector pos) {
		Entity BGblock = createEnt(name, pos);
		BGblock.getRender().setScale(new Vector(myGridSize, myGridSize));
		myBGLayer.addEntity(BGblock);
		return BGblock;
	}
	
	/**
	 * add static entities that are not background
	 * @param name
	 * @param pos
	 * @param level
	 * @param size
	 * @return created entity
	 */
	public Entity addNonBG(String name, Vector pos, int level, Vector size) {
		Entity staEnt = createEnt(name, pos);
		staEnt.getRender().setScale(size);
		
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
	 * @param name
	 * @param pos
	 * @param level
	 * @param size
	 * @return Entity
	 */
	public Entity addNonStatic(String name, Vector pos, int level, Vector size) {
		Entity Ent = addNonBG(name, pos, level, size);
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
