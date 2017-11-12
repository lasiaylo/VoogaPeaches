package engine.scripts;

import java.io.File;
import java.io.IOException;

import org.codehaus.groovy.control.CompilationFailedException;

import engine.entities.Entity;
import groovy.lang.GroovyClassLoader;

/**Modifies qualities of Entity through Groovy
 *
 * @author lasia
 * @author Albert
 */
public class Script implements IScript{
	private static final String FILEPATH = "src/engine/scripts/";
	private IScript myScript;
	
    public Script(String filename) throws InstantiationException, IllegalAccessException, CompilationFailedException, IOException {
    	GroovyClassLoader gcl = new GroovyClassLoader();
		Class<?> clazz = gcl.parseClass(new File (FILEPATH + filename) );
		Object groovyScript = clazz.newInstance();
		myScript = (IScript) groovyScript;
    }
    
    @Override
    public void execute(Entity entity) {
    	myScript.execute(entity);
    }
    
}
