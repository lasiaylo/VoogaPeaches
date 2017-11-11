package engine.scripts;

import java.util.ArrayList;
import java.util.List;

import engine.entities.Entity;

/**Scripts contained by this class will be executed if conditions are met
 * 
 * @author lasia
 *
 */
public abstract class Conditional implements IScript{
	private List<IScript> myScripts;
	
	/** Creates a new Conditional
	 * @param scripts
	 */
	public Conditional(List<IScript> scripts) {
		myScripts = scripts;
	}
	
	/** Creates a new Conditional
	 * 
	 */
	public Conditional() {
		this(new ArrayList<IScript>());
	}
	
	/**
	 * @return List of attributes that are controlled by this Conditional
	 */
	public List<IScript> getScripts() {
		return myScripts;
	}
	
	/* (non-Javadoc)
	 * @see engine.scripts.IScript#execute(engine.entities.Entity)
	 */
	public void execute(Entity entity) {
		if (conditionMet()) {
			executeScripts(entity);
		}
	}
	
	/** Checks whether the condition is met
	 * @return Boolean on whether condition was met
	 */
	protected abstract boolean conditionMet();

	/**Runs through the list of children scripts and execute each
	 * @param entity
	 */
	private void executeScripts(Entity entity) {
		for(IScript script : getScripts()) {
			script.execute(entity);
		}
	}
	

}
