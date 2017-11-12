package engine.testing;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.groovy.control.CompilationFailedException;

import engine.entities.Entity;
import engine.scripts.IScript;
import engine.scripts.Script;

public class EntityTest {
	private static final Number id = 5;
	private static final double POS = 5;
	
	
	public static void main(String[] args) throws CompilationFailedException, InstantiationException, IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		List<IScript> myScripts = new ArrayList<IScript>();

		Entity test = new Entity(id,null,myScripts,POS,POS);
		Script testScript = new Script("GroovyScript.groovy");
		test.getScripts().add(testScript);
		
		test.update();
		
		
	}
}
