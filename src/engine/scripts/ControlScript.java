package engine.scripts;


import engine.entities.Entity;

import java.util.List;

/**Scripts contained by this class will be executed if conditions are met
 * 
 * @author lasia
 *
 */
public abstract class ControlScript implements IScript{
	private List<IScript> myScripts;
	
	/**
	 * @return List of attributes that are controlled by this script
	 */
	public List<IScript> getAttributes() {
		return myScripts;
	}
	
	

}
