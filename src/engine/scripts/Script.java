package engine.scripts;

import engine.entities.Entity;
import groovy.lang.GroovyClassLoader;
import groovy.util.Eval;

/**
 * Modifies qualities of Entity through Groovy
 *
 * @author lasia
 * @author Albert
 */
public class Script implements IScript  { // made Script nonabstract since it just contains a groovy script execution
	private IScript myScript;
    public Script(String groovyClass) throws InstantiationException, IllegalAccessException, ClassCastException {
    	GroovyClassLoader gcl = new GroovyClassLoader();
		Class<?> clazz = gcl.parseClass(groovyClass);
		Object groovyScript = clazz.newInstance();
		try {
			myScript = (IScript) groovyScript;
		} catch (ClassCastException e) {
			throw new ClassCastException("Script was not formatted correctly");
		}

    }
    
    @Override
    public void execute(Entity entity) {
    	myScript.execute(entity);
    }
    
}
