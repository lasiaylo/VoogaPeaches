package engine.entities;

import java.util.ArrayList;
import java.util.List;

import engine.renderer.DBRenderer.Renderer;
import engine.scripts.IScript;
import javafx.scene.image.Image;
import util.math.num.Vector;

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
	public EntityManager(int gridSize) {
		myGridSize = gridSize;
		myBGLayer = new Layer();
		myRenderer = new Renderer(DBNAME); //probably not the right way to render, have to figure out later
		myLayerList = new ArrayList<Layer>();
	}
	
	private Entity add(String name, Vector pos) {
		Image someimage = new Image("resources/graphics/sprite_test.png");  //need to get from the renderer
		//Entity myEnt = new Entity(pos, SCRIPTL, someimage);
		//return myEnt;
		return null;
	}
	
	/**
	 * add background block
	 * @param name
	 * @param pos
	 */
	public void addBG(String name, Vector pos) {
		Entity BGblock = add(name, pos);
		BGblock.resize(myGridSize, myGridSize);
		myBGLayer.addEntity(BGblock);
	}
	
	/**
	 * add static entities that are not background
	 * @param name
	 * @param pos
	 * @param level
	 * @param width
	 * @param height
	 * @return created entity
	 */
	public Entity addNonBG(String name, Vector pos, int level, int width, int height) {
		Entity staEnt = add(name, pos);
		staEnt.resize(width, height);
		
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
	 * @param width
	 * @param height
	 */
	public void addNonStatic(String name, Vector pos, int level, int width, int height) {
		Entity Ent = addNonBG(name, pos, level, width, height);
		Ent.setMovable();
	}
	
	/**
	 * get all the nonBG entities
	 * @return non-background entities
	 */
	public List<Entity> getNonBGEntity() {
		if (myLayerList != null) {
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

}
