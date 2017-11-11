package engine.scripts;

import java.util.ArrayList;
import java.util.List;

/**Scripts contained by this class will be executed if conditions are met
 * 
 * @author lasia
 *
 */
public abstract class Conditional implements IScript{
	private List<IScript> myScripts;
	
	public Conditional(List<IScript> scripts) {
		myScripts = scripts;
	}
	
	public Conditional() {
		this(new ArrayList<IScript>());
	}
	
	/**
	 * @return List of attributes that are controlled by this Conditional
	 */
	public List<IScript> getScripts() {
		return myScripts;
	}
	
	

}
