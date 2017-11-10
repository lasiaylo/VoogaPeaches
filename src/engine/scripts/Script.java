package engine.scripts;

import engine.entities.Entity;

/**Modifies qualities of Entity through Groovy
 *
 * @author Albert
 */
public abstract class Script implements IScript{
	private IScript myScript;
    public Script() {
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
