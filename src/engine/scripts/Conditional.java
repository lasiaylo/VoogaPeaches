package engine.scripts;

import java.util.ArrayList;
import java.util.List;

import engine.entities.Entity;
import engine.managers.IManager;

/**Scripts contained by this class will be executed if conditions are met
 * 
 * @author lasia
 *
 */
public abstract class Conditional implements IScript{
	private List<IScript> myScripts;
	private Object myConditionObject;
	private IManager myManager;
	
	/** Creates a new Conditional
	 * @param List of Scripts to run when conditions are met
	 */
	public Conditional(IManager manager,Object object,List<IScript> scripts) {
		myManager = manager;
		myConditionObject = object;
		myScripts = scripts;
	}
	
	/** Creates a new Conditional
	 * 
	 */
	public Conditional(IManager manager, Object object) {
		this(manager,object,new ArrayList<IScript>());
	}
	
	/**
	 * @return List of attributes that are controlled by this Conditional
	 */
	public List<IScript> getScripts() {
		return myScripts;
	}
	
	/** 
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
	private boolean conditionMet() {
		return myManager.check(myConditionObject);
	}

	/**Runs through the list of children default and execute each
	 * @param entity
	 */
	private void executeScripts(Entity entity) {
		for(IScript script : getScripts()) {
			script.execute(entity);
		}
	}
}
