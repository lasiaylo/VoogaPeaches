package engine.scripts;


import engine.entities.Entity;

import java.util.List;

/**Scripts that directly affect attributes of an entity
 * 
 * @author lasia
 *
 */
public abstract class ControlScript extends Script{
	private List<AttributeScript> myAttributes;
	
	/**
	 * @param entity
	 */
	public ControlScript(Entity entity) {
		super(entity);
	}
	
	/**
	 * @return list of attributes that are controlled by this script
	 */
	public List<AttributeScript> getAttributes() {
//		Should List be of Scripts or AttributeScripts?
		return myAttributes;
	}

}
