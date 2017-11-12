package engine.scripts;

import engine.entities.Entity;
import groovy.lang.GroovyClassLoader;
import groovy.util.Eval;

/**Modifies qualities of Entity through Groovy
 *
 * @author lasia
 * @author Albert
 */
public abstract class Script implements IScript{
	private IScript myScript;
    public Script() throws InstantiationException, IllegalAccessException {
    	GroovyClassLoader gcl = new GroovyClassLoader();
		Class<?> clazz = gcl.parseClass("SomeName.groovy");
		Object groovyScript = clazz.newInstance();
		myScript = (IScript) groovyScript;
		
    }
    
    @Override
    public void execute(Entity entity) {
    	myScript.execute(entity);
    }
    
}
