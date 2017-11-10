package engine.scripts;

import java.util.List;

/**Scripts contained by this class will be executed if conditions are met
 * 
 * @author lasia
 *
 */
public abstract class Conditional implements IScript{
	private List<IScript> myScripts;
	
	/**
	 * @return List of attributes that are controlled by this script
	 */
	public List<IScript> getScripts() {
		return myScripts;
	}
	
	

}
